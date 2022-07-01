package me.fan87.spookysky.loader

import me.fan87.regbex.RegbexPattern
import me.fan87.regbex.utils.toInsnList
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.insertInstructions
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.io.File
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation
import java.lang.reflect.Modifier
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.security.ProtectionDomain
import java.util.*
import java.util.jar.JarFile
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.system.exitProcess

object Main {

    init {
        println("[SpookySky Loader] Initializing with class loader: ${javaClass.classLoader.javaClass.name}")
    }

    @JvmStatic
    val classes = HashMap<String, ClassNode>()
    @JvmStatic
    val bytecode = HashMap<String, ByteArray>()

    @JvmStatic
    var instrumentation: Instrumentation? = null

    @JvmStatic
    lateinit var injectTarget: File

    @JvmStatic
    val transformer = Transformer()

    @JvmStatic
    val hookedClassLoaders = ArrayList<String>()

    @JvmStatic
    var customClassLoader: CustomClassLoader? = null

    @JvmStatic
    val resourcesFile = File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString() + ".spookysky")

    @JvmStatic
    var args: String = ""

    @JvmStatic
    fun premain(args: String, instrumentation: Instrumentation) {
        println("[SpookySky Loader] Attempting to load SpookySky @ $args")
        this.instrumentation = instrumentation
        injectTarget = File(args)
        this.args = args
        if (!injectTarget.exists()) {
            println("[SpookySky Loader] Agent file doesn't exist: $args")
            exitProcess(-1)
        }
        if (injectTarget.extension != "jar") {
            println("[SpookySky Loader] Agent file is not a jar file: $args")
            exitProcess(-1)
        }

        instrumentation.addTransformer(transformer, true)

        val res = Main::class.java.classLoader.getResource("spookysky-resources/")!!
        val path = res.toURI()
        val jarFile = JarFile(res.path.split(":").let { it.subList(1, it.size).joinToString(":") }.split("!")[0])
        for (entry in jarFile.entries()) {
            if (entry.name.startsWith("spookysky-resources/")) {
                if (entry.name.substring("spookysky-resources/".length).length == 0) continue
                if (entry.name.endsWith("/")) continue
                val file = File(resourcesFile, entry.name.substring("spookysky-resources/".length))
                file.parentFile.mkdirs()
                file.createNewFile()
                file.writeBytes(jarFile.getInputStream(entry).readBytes())
            }
        }

    }

    var loaded = false;

    @JvmStatic
    fun classLoaderHook(obj: Any) {
        if (loaded) {
            return;
        }
        loaded = true;
        if (javaClass.classLoader != ClassLoader.getSystemClassLoader().loadClass(Main::class.java.name).classLoader) {
            println("[SpookySky Loader] The loader class isn't loaded correctly! Using the one from it... (${javaClass.classLoader.javaClass.name})")
            val main = ClassLoader.getSystemClassLoader().loadClass(Main::class.java.name)
            main.getDeclaredMethod("classLoaderHook", Any::class.java).invoke(null, obj)
            return;
        }

        println("[SpookySky Loader] Being called with class loader: ${javaClass.classLoader.javaClass.name}")
        try {
            val loader = obj.javaClass.classLoader
            println("[SpookySky Loader] Found ClassLoader: $loader  Super Class: ${classes[loader.javaClass.name.replace(".", "/")]?.superName}")
            if (classes[loader.javaClass.name.replace(".", "/")] == null) {
                println("[SpookySky Loader] Class has not been transformed yet! Re-transforming it...")
                instrumentation!!.retransformClasses(loader.javaClass)
                loaded = false
                classLoaderHook(obj)
                return
            }
            // LaunchClassLoader
            if (loader.javaClass.name.replace(".", "/") in hookedClassLoaders) {
                println("[SpookySky Loader] Detected Hooked Class Loader! Attempting to inject to it...  (Using strategy: Hook Custom Loader)")
                customClassLoader = CustomClassLoader(loader)
                customClassLoader!!.addURL(File(args).toURI().toURL())
                var text = "kysky.client.SpookySk"
                text += "y"
                val loadClass = loader.loadClass("me.fan87.spoo" + text) // Avoid shadowJar doing weird stuff
                val spookySkyInstance = loadClass.getConstructor(Instrumentation::class.java, HashMap::class.java, ClassFileTransformer::class.java, File::class.java, ClassLoader::class.java)
                    .newInstance(instrumentation, HashMap(bytecode), transformer, resourcesFile, loader)
            } else if (loader is URLClassLoader) {
                println("[SpookySky Loader] Detected URLClassLoader! Attempting to inject to it... (Using strategy: addURL)")
                val addURLMethod = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
                try {
                    addURLMethod.isAccessible = true
                } catch (e: Exception) {
                    println("[SpookySky Loader] Failed to load SpookySky! If it's later Java version, please add JVM argument: `--add-opens java.base/java.net=ALL-UNNAMED`")
                    exitProcess(-1)
                }
                addURLMethod.invoke(loader, File(args).toURI().toURL());
                var text = "kysky.client.SpookySk"
                text += "y"

                val loadClass = loader.loadClass("me.fan87.spoo" + text)
                val spookySkyInstance = loadClass.getConstructor(Instrumentation::class.java, HashMap::class.java, ClassFileTransformer::class.java, File::class.java, ClassLoader::class.java)
                    .newInstance(instrumentation, HashMap(bytecode), transformer, resourcesFile, loader)
            } else {
                /**
                 * This part of code is untested!
                 */

                println("[SpookySky Loader] Target class loader is not an instance of URLClassLoader! Loading with App Class Loader (Using strategy: Agent Load)")
                SpookySky(instrumentation!!, HashMap(bytecode), transformer, resourcesFile, Main::class.java.classLoader)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            e.printStackTrace(System.out)
            println("[SpookySky Loader] Error! Quitting...")
            exitProcess(-1)
        }

    }

    @JvmStatic
    fun tryFindClass(e: Throwable, name: String): Class<*> {
        if (customClassLoader == null) {
            throw e
        } else {
            try {
                return customClassLoader!!.findClass(name)
            } catch (e: LinkageError) {
                return customClassLoader!!.loadClass(name)
            }
        }
    }


}

class Transformer: ClassFileTransformer {
    override fun transform(
        loader: ClassLoader,
        className: String,
        classBeingRedefined: Class<*>?,
        protectionDomain: ProtectionDomain,
        classfileBuffer: ByteArray
    ): ByteArray {
        if (className.startsWith("me/fan87/spookysky")) {
            return classfileBuffer;
        }
        if (className.startsWith("jdk") || className.startsWith("java") || className.startsWith("sun")) {
            return classfileBuffer
        }
        val reader = ClassReader(classfileBuffer)
        val node = ClassNode()
        reader.accept(node, ClassReader.EXPAND_FRAMES)
        Main.classes[node.name] = node
        Main.bytecode[node.name] = classfileBuffer

        try {
            if (node.superName == "java/net/URLClassLoader") {

            val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
            node.accept(writer)
            writer.toByteArray()

            Main.hookedClassLoaders.add(node.name)
            var method =
                node.methods.firstOrNull { it.name == "findClass" && it.desc == "(Ljava/lang/String;)Ljava/lang/Class;" }
            println("[SpookySky Loader] Attempting to inject to custom class loader ${node.name} (To Hook: ${method?.name})")
            if (method != null) {
                val start = LabelNode()
                val catch = LabelNode()
                val endOfMethod = LabelNode()
                val out = InsnList()
                out.add(start)
                out.add(method.instructions)
                out.add(JumpInsnNode(Opcodes.GOTO, endOfMethod))
                out.add(catch)
                out.add(TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Throwable"))
                out.add(VarInsnNode(Opcodes.ALOAD, 1))
                out.add(MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    Main::class.java.name.replace(".", "/"),
                    "tryFindClass",
                    "(Ljava/lang/Throwable;Ljava/lang/String;)Ljava/lang/Class;"
                ))
                out.add(InsnNode(Opcodes.ARETURN))
                out.add(endOfMethod)
                method.instructions = out
                method.tryCatchBlocks = ArrayList()
                method.tryCatchBlocks.add(TryCatchBlockNode(start, catch, catch, "java/lang/Throwable"))
            } else {
                /**
                 * TODO: This part of code is untested!
                 */
                val method = MethodNode()
                val start = LabelNode()
                val catch = LabelNode()
                val endOfMethod = LabelNode()
                method.name = "findClass"
                method.desc = "(Ljava/lang/String;)Ljava/lang/Class;"
                method.instructions.add(start)
                method.instructions.add(VarInsnNode(Opcodes.ALOAD, 0))
                method.instructions.add(VarInsnNode(Opcodes.ALOAD, 1))
                method.instructions.add(ASMUtils.generateMethodCall(URLClassLoader::class.java.getDeclaredMethod("findClass", String::class.java)))
                method.instructions.add(InsnNode(Opcodes.ARETURN))
                method.instructions.add(JumpInsnNode(Opcodes.GOTO, endOfMethod))
                method.instructions.add(catch)
                method.instructions.add(VarInsnNode(Opcodes.ALOAD, 1))
                method.instructions.add(MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    Main::class.java.name.replace(".", "/"),
                    "tryFindClass",
                    "(Ljava/lang/Throwable;Ljava/lang/String;)Ljava/lang/Class;"
                ))
                method.instructions.add(InsnNode(Opcodes.ARETURN))
                method.instructions.add(endOfMethod)
                method.tryCatchBlocks = ArrayList()
                method.tryCatchBlocks.add(TryCatchBlockNode(start, catch, catch, "java/lang/Throwable"))
                node.methods.add(method)
            }
            val out = ASMUtils.writeClass(node)
            return out
        } else {
                for (method in node.methods) {
                    for (instruction in method.instructions) {
                        if (instruction is LdcInsnNode && instruction.cst is String) {
                            if ((instruction.cst as String).contains("Setting user:"))  {
                                println("[SpookySky Loader] Found Minecraft Class: ${node.name}")
                                for (methodToInject in node.methods) {
                                    if (methodToInject.name.startsWith("<")) {
                                        continue
                                    }
                                    if (Modifier.isStatic(methodToInject.access)) {
                                        continue
                                    }
                                    val out = InsnList()
                                    out.add(VarInsnNode(Opcodes.ALOAD, 0))
                                    out.add(MethodInsnNode(
                                        Opcodes.INVOKESTATIC,
                                        Main::class.java.name.replace(".", "/"),
                                        "classLoaderHook",
                                        "(Ljava/lang/Object;)V"
                                    ))
                                    out.add(methodToInject.instructions)
                                    methodToInject.instructions = out
                                }

                                val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
                                node.accept(writer)
                                return writer.toByteArray()
                            }
                        }
                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace(System.out)
        }
        return classfileBuffer
    }

}