package me.fan87.spookysky.client.commands.impl

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.commands.Command
import me.fan87.spookysky.client.utils.ChatColor

class CommandMapping: Command(
    "mapping",
    "List all mappings and their status",
    ""
) {
    override fun onCommand(args: Array<String>) {
        for (mapping in spookySky.mappingsManager.mappings) {
            SpookySky.addClientChat("${if (mapping.isMapped()) ChatColor.GREEN else ChatColor.RED}${mapping.humanReadableName}${if (mapping.isMapped()) "${ChatColor.GRAY} - ${mapping.mapped!!.name}" else ""}")
            for (child in mapping.children) {
                SpookySky.addClientChat("  ${if (child.isMapped()) ChatColor.GREEN else ChatColor.RED}${child.humanReadableName}${if (child.isMapped()) "${ChatColor.GRAY} - ${child.mapped!!.name}" else ""}")
            }
        }
    }
}