package me.fan87.spookysky.client

import me.fan87.spookysky.client.commands.CommandsManager
import me.fan87.spookysky.client.events.EventsManager
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.IChatComponent
import me.fan87.spookysky.client.module.ModulesManager
import me.fan87.spookysky.client.processors.ProcessorsManager
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ChatColor
import org.objectweb.asm.tree.ClassNode
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation

class SpookySky(
    val instrumentation: Instrumentation,
    val preLoadedClasses: HashMap<String, ClassNode>,
    val transformer: ClassFileTransformer
) {

    companion object {
        lateinit var INSTANCE: SpookySky


        fun debug(message: Any) {
            println("[SpookySky] $message")
        }

        fun addClientChat(message: String) {
            Minecraft.getMinecraft().ingameGui?.getChatGUI()?.printChatMessage(ChatComponentText("${ChatColor.BLUE}[SpookySky] ${ChatColor.RESET}$message"))
        }
        fun addClientChat(component: IChatComponent) {
            Minecraft.getMinecraft().ingameGui?.getChatGUI()?.printChatMessage(component)
        }
    }
    val classes = HashMap<String, LoadedClass>()

    val mappingsManager: MappingsManager
    val processorsManager: ProcessorsManager
    val modulesManager: ModulesManager
    val commandsManager: CommandsManager

    val eventManager = EventsManager()

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



}