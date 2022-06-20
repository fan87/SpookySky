package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping

object MapChatComponentStyle: ClassMapping<ChatComponentStyle>() {
    override fun getWrapperClass(): Class<ChatComponentStyle> {
        return ChatComponentStyle::class.java
    }

    override val humanReadableName: String
        get() = "ChatComponentStyle"


}

open class ChatComponentStyle protected constructor(original: Any): IChatComponent(original) {


}