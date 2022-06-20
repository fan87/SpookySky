package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapChatComponentTranslation: ClassMapping<ChatComponentTranslation>() {
    override val humanReadableName: String
        get() = "ChatComponentTranslation"


}

class ChatComponentTranslation private constructor(original: Any): ChatComponentStyle(original) {

    companion object {
        operator fun invoke(translationKey: String, vararg args: Any): ChatComponentTranslation {
            return ChatComponentTranslation(MapChatComponentTranslation(translationKey, args))
        }
        fun fromOriginal(original: Any): ChatComponentTranslation {
            return ChatComponentTranslation(original)
        }
    }

}