package me.fan87.spookysky.loader

import me.fan87.regbex.RegbexPattern
import me.fan87.regbex.utils.toInsnList
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.utils.ASMUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.io.File
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation
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
    val classes = HashMap<String, ClassNode>()
    lateinit var instrumentation: Instrumentation
    lateinit var injectTarget: File
    val transformer = Transformer()

    val hookedClassLoaders = ArrayList<String>()

    var customClassLoader: CustomClassLoader? = null

    val resourcesFile = File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString() + ".spookysky")

    @JvmStatic
    fun premain(args: String, instrumentation: Instrumentation) {
        this.instrumentation = instrumentation
        injectTarget = File(args)
        if (!injectTarget.exists()) {
            println("[SpookySky Loader] Agent file doesn't exist: $args")
            exitProcess(-1)
        }
        if (injectTarget.extension != "jar") {
            println("[SpookySky Loader] Agent file is not a jar file: $args")
            exitProcess(-1)
        }

        instrumentation.addTransformer(transformer)

        val res = Main::class.java.classLoader.getResource("spookysky-resources/")!!
        val path = res.toURI()
        val jarFile = JarFile(res.path.split(":")[1].split("!")[0])
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

    @JvmStatic
    fun classLoaderHook(obj: Any) {
        try {
            val loader = obj.javaClass.classLoader
            println("[SpookySky Loader] Found ClassLoader: $loader")
            if (loader.javaClass.name.replace(".", "/") in hookedClassLoaders) {
                println("[SpookySky Loader] Detected Hooked Class Loader! Attempting to inject to it...  (Using strategy: Hook Custom Loader)")
                customClassLoader = CustomClassLoader(loader)
                customClassLoader!!.addURL(injectTarget.toURI().toURL())
                var text = "kysky.client.SpookySk"
                text += "y"
                val loadClass = loader.loadClass("me.fan87.spoo" + text) // Avoid shadowJar doing weird stuff
                val spookySkyInstance = loadClass.getConstructor(Instrumentation::class.java, HashMap::class.java, ClassFileTransformer::class.java, File::class.java)
                    .newInstance(instrumentation, HashMap(classes), transformer, resourcesFile)
            } else if (loader is URLClassLoader) {
                println("[SpookySky Loader] Detected URLClassLoader! Attempting to inject to it... (Using strategy: addURL)")
                val addURLMethod = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
                try {
                    addURLMethod.isAccessible = true
                } catch (e: Exception) {
                    println("[SpookySky Loader] Failed to load SpookySky! If it's later Java version, please add JVM argument: `--add-opens java.base/java.net=ALL-UNNAMED`")
                    exitProcess(-1)
                }
                addURLMethod.invoke(loader, injectTarget.toURI().toURL());
                var text = "kysky.client.SpookySk"
                text += "y"

                val loadClass = loader.loadClass("me.fan87.spoo" + text)
                val spookySkyInstance = loadClass.getConstructor(Instrumentation::class.java, HashMap::class.java, ClassFileTransformer::class.java, File::class.java)
                    .newInstance(instrumentation, HashMap(classes), transformer, resourcesFile)
            } else {
                /**
                 * This part of code is untested!
                 */

                println("[SpookySky Loader] Target class loader is not an instance of URLClassLoader! Loading with App Class Loader (Using strategy: Agent Load)")
                SpookySky(instrumentation, HashMap(classes), transformer, resourcesFile)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
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
    val pattern = RegbexPattern {
        thenLdcStringEqual("Manually triggered debug crash")
    }
    val returnPattern = RegbexPattern {
        thenReturn()
    }
    override fun transform(
        loader: ClassLoader,
        className: String,
        classBeingRedefined: Class<*>?,
        protectionDomain: ProtectionDomain,
        classfileBuffer: ByteArray
    ): ByteArray {
        if (className.startsWith("jdk") || className.startsWith("java") || className.startsWith("sun")) {
            return classfileBuffer
        }
        val reader = ClassReader(classfileBuffer)
        val node = ClassNode()
        reader.accept(node, ClassReader.EXPAND_FRAMES)
        Main.classes[node.name] = node

        if (node.superName == "java/net/URLClassLoader") {
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
                out.add(ASMUtils.generateMethodCall(Main::tryFindClass))
                out.add(InsnNode(Opcodes.ARETURN))
                out.add(endOfMethod)
                method.instructions = out
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
                method.instructions.add(ASMUtils.generateMethodCall(Main::tryFindClass))
                method.instructions.add(InsnNode(Opcodes.ARETURN))
                method.instructions.add(endOfMethod)

                method.tryCatchBlocks.add(TryCatchBlockNode(start, catch, catch, "java/lang/Throwable"))
                node.methods.add(method)
            }
            return ASMUtils.writeClass(node)
        } else {
            for (method in node.methods) {
                val matcher = pattern.matcher(method)
                if (matcher.next(0)) {
                    println("[SpookySky Loader] Found Minecraft Class: ${node.name}")
                    val methodToInject = node.methods.first { it.name == "<init>" }
                    val replaceMatcher = returnPattern.replaceAll(methodToInject.instructions, InsnList().also {
                        it.add(VarInsnNode(Opcodes.ALOAD, 0))
                        it.add(ASMUtils.generateMethodCall(Main::classLoaderHook))
                        it.add(InsnNode(Opcodes.RETURN))
                    })

                    methodToInject.instructions = replaceMatcher.toInsnList()

                    val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
                    node.accept(writer)
                    return writer.toByteArray()
                }
            }
        }

        return classfileBuffer
    }

}