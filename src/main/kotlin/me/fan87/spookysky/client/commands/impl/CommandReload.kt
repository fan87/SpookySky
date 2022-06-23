package me.fan87.spookysky.client.commands.impl

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.commands.Command
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.ChatStyle
import me.fan87.spookysky.client.mapping.impl.chat.EnumChatFormatting.Companion.toEnumChatFormatting
import me.fan87.spookysky.client.render.ShaderProgram
import me.fan87.spookysky.client.utils.ChatColor

class CommandReload: Command("reload", "Reload everything that's possible to be reloaded", "") {
    override fun onCommand(args: Array<String>) {
        SpookySky.addClientChat(ChatComponentText("Reloading...").setColor(ChatColor.YELLOW))
        SpookySky.addClientChat(ChatComponentText(" - Reloading Shaders..").setColor(ChatColor.YELLOW))
        for (createdShader in ShaderProgram.createdShaders) {
            createdShader.reloadShader()
        }
        SpookySky.addClientChat(ChatComponentText(" - Shader has been reloaded!").setColor(ChatColor.GREEN))
        SpookySky.addClientChat(ChatComponentText("Reload Finished!").setColor(ChatColor.GREEN))
    }
}