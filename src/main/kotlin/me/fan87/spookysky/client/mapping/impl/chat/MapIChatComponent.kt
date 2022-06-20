package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.chat.EnumChatFormatting.Companion.toEnumChatFormatting
import me.fan87.spookysky.client.utils.ChatColor

object MapIChatComponent: ClassMapping<IChatComponent>() {
    override val humanReadableName: String
        get() = "IChatComponent"

    val mapAppendSibling = MethodMapping<Any, IChatComponent>(this, "appendSibling(IChatComponent)")
    val mapSetChatStyle = MethodMapping<Any, IChatComponent>(this, "setChatStyle(ChatStyle)")
    val mapGetChatStyle = MethodMapping<Any, IChatComponent>(this, "getChatStyle()")

}

open class IChatComponent(original: Any): WrapperClass(original) {

    fun appendSibling(component: IChatComponent): IChatComponent = IChatComponent(MapIChatComponent.mapAppendSibling.invoke(this, component)!!)
    fun setChatStyle(chatStyle: ChatStyle): IChatComponent = IChatComponent(MapIChatComponent.mapSetChatStyle.invoke(this, chatStyle.original)!!)
    fun getChatStyle(): ChatStyle = ChatStyle.fromOriginal(MapIChatComponent.mapGetChatStyle.invoke(this)!!)
    fun appendText(text: String): IChatComponent = appendSibling(ChatComponentText(text))

    fun setColor(color: ChatColor): IChatComponent {
        setChatStyle(getChatStyle().apply {
            this.color = color.toEnumChatFormatting()
        })
        return this
    }

}