package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping

object MapChatComponentStyle: ClassMapping<ChatComponentStyle>() {
    override val humanReadableName: String
        get() = "ChatComponentStyle"


}

open class ChatComponentStyle(original: Any): IChatComponent(original) {


}