package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.utils.ChatColor

object MapChatStyle: ClassMapping<ChatStyle>() {
    override fun getWrapperClass(): Class<ChatStyle> {
        return ChatStyle::class.java
    }

    override val humanReadableName: String
        get() = "ChatStyle"

    val mapCreateShallowCopy = MethodMapping<Any, ChatStyle>(this, "createShallowCopy()")

    val mapParentStyle = NullableFieldMapping<Any, ChatStyle>(this, "parentStyle")
    val mapColor = NullableFieldMapping<Any, ChatStyle>(this, "color")
    val mapBold = NullableFieldMapping<Boolean, ChatStyle>(this, "bold")
    val mapItalic = NullableFieldMapping<Boolean, ChatStyle>(this, "italic")
    val mapUnderlined = NullableFieldMapping<Boolean, ChatStyle>(this, "underlined")
    val mapStrikethrough = NullableFieldMapping<Boolean, ChatStyle>(this, "strikethrough")
    val mapObfuscated = NullableFieldMapping<Boolean, ChatStyle>(this, "obfuscated")
}

open class ChatStyle protected constructor(original: Any): WrapperClass(original) {

    companion object {
        operator fun invoke(): ChatStyle {
            return fromOriginal(MapChatStyle())
        }
        fun fromOriginal(original: Any): ChatStyle {
            return MappingsManager.getWrapped(original)
        }
    }

    fun createShallowCopy(): ChatStyle = MappingsManager.getWrapped(MapChatStyle.mapCreateShallowCopy.invoke(this)!!)

    var parentStyle by NullableWrappedFieldType(MapChatStyle.mapParentStyle, ChatStyle::class.java)
    var color by NullableWrappedFieldType(MapChatStyle.mapColor, EnumChatFormatting::class.java)
    var bold by MapChatStyle.mapBold
    var italic by MapChatStyle.mapItalic
    var underlined by MapChatStyle.mapUnderlined
    var strikethrough by MapChatStyle.mapStrikethrough
    var obfuscated by MapChatStyle.mapObfuscated

}