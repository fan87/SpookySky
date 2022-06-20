package me.fan87.spookysky.client.commands.impl

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.commands.Command
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.ChatStyle
import me.fan87.spookysky.client.mapping.impl.chat.EnumChatFormatting.Companion.toEnumChatFormatting
import me.fan87.spookysky.client.utils.ChatColor

class CommandHelp: Command(
    "help",
    "List all commands available",
    "",
    "cmds"
) {
    override fun onCommand(args: Array<String>) {
        for (command in spookySky.commandsManager.commands) {
            SpookySky.addClientChat(
                ChatComponentText("${command.name} ${command.argumentsUsage}  ").setColor(ChatColor.WHITE)
                .appendSibling(ChatComponentText("-  ${command.description}").setColor(ChatColor.GRAY))

            )
        }
    }
}