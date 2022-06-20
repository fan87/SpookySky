package me.fan87.spookysky.client.commands

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.AutoCompleteEvent
import me.fan87.spookysky.client.events.events.GuiChatMessageEvent
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.ChatStyle
import me.fan87.spookysky.client.mapping.impl.chat.EnumChatFormatting.Companion.toEnumChatFormatting
import me.fan87.spookysky.client.mapping.impl.rendering.GuiChat
import me.fan87.spookysky.client.mapping.impl.rendering.GuiIngame
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiChat
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiIngame
import me.fan87.spookysky.client.utils.ChatColor
import org.apache.logging.log4j.core.config.plugins.ResolverUtil
import java.lang.reflect.Modifier
import java.net.URI

class CommandsManager(val spookySky: SpookySky) {

    val commands = ArrayList<Command>()

    init {
        spookySky.eventManager.registerListener(this)

        val resolver = ResolverUtil()
        resolver.classLoader = javaClass.classLoader
        resolver.findInPackage(object : ResolverUtil.Test {
            override fun matches(type: Class<*>?): Boolean {
                return Command::class.java.isAssignableFrom(type) && !Modifier.isAbstract(type!!.modifiers)
            }

            override fun matches(resource: URI?): Boolean {
                return true
            }

            override fun doesMatchClass(): Boolean {
                return true
            }

            override fun doesMatchResource(): Boolean {
                return false
            }
        }, javaClass.`package`.name)

        for (clazz in resolver.classes) {
            val command = clazz.newInstance() as Command
            SpookySky.debug("[Commands Manager] Registered Command: ${command.name}")
            commands.add(command)
        }
    }


    inline fun <reified T: Command> getCommand(): T {
        return commands.first { it.javaClass == T::class.java } as T
    }

    @EventHandler
    fun onAutoComplete(event: AutoCompleteEvent) {
        try {
            if (event.message.startsWith(".")) {
                val guiChat = Minecraft.getMinecraft().currentScreen
                if (guiChat !is GuiChat) {
                    return
                }
                guiChat.waitingOnAutocomplete = true
                event.cancelled = true
                val input = event.message.substring(1)
                val split = input.split(" ")
                val commandName = split[0]
                val command = commands.firstOrNull { it.name == commandName || it.aliases.contains(commandName) }
                if (command == null) {
                    val output = ArrayList<String>()
                    for (registeredCommand in commands) {
                        if (registeredCommand.name.startsWith(commandName)) {
                            output.add("." + registeredCommand.name)
                        }
                    }
                    guiChat.onAutocompleteResponse(output.toTypedArray())
                    return
                }
                val args = split.subList(1, split.size).toTypedArray()
                command.args = args
                var result = command.onTabComplete(args)
                guiChat.onAutocompleteResponse(result.toTypedArray())

            }
        } catch (e: CommandException) {

        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    @EventHandler
    fun onChat(event: GuiChatMessageEvent) {
        try {
            if (event.message.startsWith(".")) {
                event.cancelled = true
                val input = event.message.substring(1)
                val split = input.split(" ")
                val commandName = split[0]
                val command = commands.firstOrNull { it.name == commandName || it.aliases.contains(commandName) }
                if (command == null) {
                    throw CommandException("Unknown Command! Type .help for commands list.")
                }
                val args = split.subList(1, split.size).toTypedArray()
                command.args = args
                command.onCommand(args)
                Minecraft.getMinecraft().ingameGui?.getChatGUI()?.addToSentMessages(event.message)
            }
        } catch (e: CommandException) {
            SpookySky.addClientChat(ChatComponentText(e.message!!).setChatStyle(ChatStyle().apply {
                color = ChatColor.RED.toEnumChatFormatting()
            }))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}

class CommandException(reason: String): Exception(reason)