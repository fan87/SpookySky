package me.fan87.spookysky.client.commands.impl

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.commands.Command
import me.fan87.spookysky.client.utils.ChatColor

class CommandHelp: Command(
    "help",
    "List all commands available",
    "",
    "cmds"
) {
    override fun onCommand(args: Array<String>) {
        for (command in spookySky.commandsManager.commands) {
            SpookySky.addClientChat("${ChatColor.GREEN}${command.name} ${command.argumentsUsage}  ${ChatColor.GRAY}-  ${command.description}")
        }
    }
}