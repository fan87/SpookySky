package me.fan87.spookysky.client

import me.fan87.spookysky.client.commands.CommandsManager
import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.EventsManager
import me.fan87.spookysky.client.events.events.RenderEndFrameEvent
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.ChatStyle
import me.fan87.spookysky.client.mapping.impl.chat.EnumChatFormatting.Companion.toEnumChatFormatting
import me.fan87.spookysky.client.mapping.impl.chat.IChatComponent
import me.fan87.spookysky.client.module.ModulesManager
import me.fan87.spookysky.client.processors.ProcessorsManager
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ChatColor
import org.lwjgl.opengl.GL11
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation

class SpookySky(
    val instrumentation: Instrumentation,
    val preLoadedClasses: HashMap<String, ClassNode>,
    val transformer: ClassFileTransformer,
    val resourcesFile: File,
    val clientClassLoader: ClassLoader
) {


    companion object {
        lateinit var INSTANCE: SpookySky

        fun getResourceAsStream(fileName: String): InputStream {
            return FileInputStream(File(INSTANCE.resourcesFile, fileName))
        }

        fun debug(message: Any) {
            println("[SpookySky] $message")
        }

        fun addClientChat(message: String) {
            addClientChat(ChatComponentText(message).setColor(ChatColor.GRAY))
        }
        fun addClientChat(component: IChatComponent) {
            Minecraft.getMinecraft().ingameGui?.getChatGUI()?.printChatMessage(ChatComponentText("${ChatColor.BLUE}SpookySky §7» ").apply {
                setChatStyle(ChatStyle().apply { color = ChatColor.BLUE.toEnumChatFormatting() })
                appendSibling(component)
            })
        }
    }
    val classes = HashMap<String, LoadedClass>()

    val mappingsManager: MappingsManager
    val processorsManager: ProcessorsManager
    val modulesManager: ModulesManager
    val commandsManager: CommandsManager

    val eventManager = EventsManager()

    var initTime = System.currentTimeMillis()

    init {
        INSTANCE = this
        debug("SpookySky has been injected to class loader: ${javaClass.classLoader}! Initializing...")
        instrumentation.addTransformer { loader, className, classBeingRedefined, protectionDomain, classfileBuffer ->
            val node = ASMUtils.parseClass(classfileBuffer)
            val name = className.replace(".", "/")
            classes[name] = LoadedClass(name, node)
            classfileBuffer
        }
        instrumentation.removeTransformer(transformer)
        for (preLoadedClass in preLoadedClasses) {
            classes[preLoadedClass.key] = LoadedClass(preLoadedClass.key, preLoadedClass.value)
        }

        mappingsManager = MappingsManager(this)
        processorsManager = ProcessorsManager(this)
        modulesManager = ModulesManager(this)
        commandsManager = CommandsManager(this)

        eventManager.registerListener(this)


    }

    @EventHandler
    fun onEndFrame(event: RenderEndFrameEvent) {
        var mapped = true
        outer@for (mapping in mappingsManager.mappings) {
            if (!mapping.isMapped()) {
                initTime = System.currentTimeMillis()
                mapped = false
                break
            }
            for (child in mapping.children) {
                if (!child.isMapped()) {
                    initTime = System.currentTimeMillis()
                    mapped = false
                    break
                }
                break@outer
            }
        }
        if (System.currentTimeMillis() - initTime <= 20000) {
            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
            GL11.glPushMatrix()

            GL11.glEnable(GL11.GL_SCISSOR_TEST)
            GL11.glScissor(0, 0, 4, 4)
            if (!mapped) {
                GL11.glClearColor(1f, 0f, 0f, 1f)
            } else {
                GL11.glClearColor(0f, 1f, 0f, 1f)
            }
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
            GL11.glDisable(GL11.GL_SCISSOR_TEST)

            GL11.glPopMatrix()
            GL11.glPopAttrib()
        }

    }


}