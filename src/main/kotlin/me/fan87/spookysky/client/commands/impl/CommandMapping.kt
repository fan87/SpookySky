package me.fan87.spookysky.client.commands.impl

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.commands.Command
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.utils.ChatColor

class CommandMapping: Command(
    "mapping",
    "List all mappings and their status",
    ""
) {
    override fun onCommand(args: Array<String>) {
        for (mapping in spookySky.mappingsManager.mappings) {
            var output = ChatComponentText(mapping.humanReadableName).setColor(if (mapping.isMapped()) ChatColor.GREEN else ChatColor.RED)
            if (mapping.isMapped()) {
                output = output.appendSibling(ChatComponentText(" - ${mapping.assumeMapped().name}").setColor(ChatColor.GRAY))
            }
            SpookySky.addClientChat(output)
            for (child in mapping.children) {
                output = ChatComponentText("  " + child.humanReadableName).setColor(if (child.isMapped()) ChatColor.GREEN else ChatColor.RED)
                if (child.isMapped()) {
                    output = output.appendSibling(ChatComponentText(" - ${child.assumeMapped().name}").setColor(ChatColor.GRAY))
                }
                SpookySky.addClientChat(output)
            }
        }
    }
}