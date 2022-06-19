package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapChatComponentText: ClassMapping<ChatComponentText>() {
    override val humanReadableName: String
        get() = "ChatComponentText"


}

class ChatComponentText private constructor(original: Any): IChatComponent(original) {

    companion object {
        operator fun invoke(string: String): ChatComponentText {
            return ChatComponentText(MapChatComponentText(string))
        }
        fun fromOriginal(original: Any): ChatComponentText {
            return ChatComponentText(original)
        }
    }

}