package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.chat.EnumChatFormatting.Companion.toEnumChatFormatting
import me.fan87.spookysky.client.utils.ChatColor

object MapIChatComponent: ClassMapping<IChatComponent>() {
    override fun getWrapperClass(): Class<IChatComponent> {
        return IChatComponent::class.java
    }

    override val humanReadableName: String
        get() = "IChatComponent"

    val mapAppendSibling = MethodMapping<Any, IChatComponent>(this, "appendSibling(IChatComponent)")
    val mapSetChatStyle = MethodMapping<Any, IChatComponent>(this, "setChatStyle(ChatStyle)")
    val mapGetChatStyle = MethodMapping<Any, IChatComponent>(this, "getChatStyle()")

}

open class IChatComponent protected constructor(original: Any): WrapperClass(original) {

    fun appendSibling(component: IChatComponent): IChatComponent = MappingsManager.getWrapped(MapIChatComponent.mapAppendSibling.invoke(this, component)!!)
    fun setChatStyle(chatStyle: ChatStyle): IChatComponent = MappingsManager.getWrapped(MapIChatComponent.mapSetChatStyle.invoke(this, chatStyle.original)!!)
    fun getChatStyle(): ChatStyle = MappingsManager.getWrapped(MapIChatComponent.mapGetChatStyle.invoke(this)!!)
    fun appendText(text: String): IChatComponent = appendSibling(ChatComponentText(text))

    fun setColor(color: ChatColor): IChatComponent {
        setChatStyle(getChatStyle().apply {
            this.color = color.toEnumChatFormatting()
        })
        return this
    }

}