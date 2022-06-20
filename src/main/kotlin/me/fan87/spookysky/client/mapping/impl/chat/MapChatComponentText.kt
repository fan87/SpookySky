package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapChatComponentText: ClassMapping<ChatComponentText>() {
    override fun getWrapperClass(): Class<ChatComponentText> {
        return ChatComponentText::class.java
    }

    override val humanReadableName: String
        get() = "ChatComponentText"


}

class ChatComponentText protected constructor(original: Any): ChatComponentStyle(original) {

    companion object {
        operator fun invoke(string: String): ChatComponentText {
            return ChatComponentText(MapChatComponentText(string))
        }
        fun fromOriginal(original: Any): ChatComponentText {
            return ChatComponentText(original)
        }
    }

}