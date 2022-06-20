package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.utils.ChatColor

object MapEnumChatFormatting: ClassMapping<EnumChatFormatting>() {
    override fun getWrapperClass(): Class<EnumChatFormatting> {
        return EnumChatFormatting::class.java
    }

    override val humanReadableName: String
        get() = "EnumChatFormatting"

    val mapGetValueByName = MethodMapping<Any, EnumChatFormatting>(this, "getValueByName(String)")
    val mapName = FieldMapping<String, EnumChatFormatting>(this, "name")

}

open class EnumChatFormatting protected constructor(original: Any): WrapperClass(original) {

    companion object {
        fun fromChatColor(color: ChatColor): EnumChatFormatting {
            return getValueByName(color.name)
        }

        fun ChatColor.toEnumChatFormatting(): EnumChatFormatting {
            return getValueByName(this.name)
        }

        fun getValueByName(name: String): EnumChatFormatting = EnumChatFormatting(MapEnumChatFormatting.mapGetValueByName.invoke(null, name)!!)
    }

    val name: String by MapEnumChatFormatting.mapName

    fun toChatColor(): ChatColor {
        return ChatColor.values().first { it.name == name }
    }


}