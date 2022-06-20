package me.fan87.spookysky.client.commands.impl

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.commands.Command
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.utils.ChatColor

class CommandToggle: Command("toggle", "Toggle a module", "<moduleName: String>") {
    override fun onCommand(args: Array<String>) {
        assertArgumentCount(1)
        val moduleName = args[0]
        val module = spookySky.modulesManager.modules.firstOrNull { it.name.equals(moduleName, true) }
            ?: fail("Module with name $moduleName could not be found!")

        module.toggled = !module.toggled
        SpookySky.addClientChat(ChatComponentText("${module.name} has been ${if (module.toggled) "enabled" else "disabled"}!").setColor(if (module.toggled) ChatColor.GREEN else ChatColor.RED))
    }

    override fun onTabComplete(args: Array<String>): List<String> {
        if (args.size == 1) {
            return spookySky.modulesManager.modules.filter { it.name.uppercase().startsWith(args[0].uppercase()) }.map { it.name }
        }
        return arrayListOf()
    }
}