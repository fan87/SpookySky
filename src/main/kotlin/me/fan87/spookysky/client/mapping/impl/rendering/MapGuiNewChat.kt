package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.chat.IChatComponent

object MapGuiNewChat: ClassMapping<GuiNewChat>() {
    override fun getWrapperClass(): Class<GuiNewChat> {
        return GuiNewChat::class.java
    }

    override val humanReadableName: String
        get() = "GuiNewChat"

    val mapAddToSentMessages = MethodMapping<Unit, GuiNewChat>(this, "addToSentMessages(String)")
    val mapPrintChatMessage = MethodMapping<Unit, GuiNewChat>(this, "printChatMessage(IChatComponent)")
}

class GuiNewChat protected constructor(original: Any): Gui(original) {

    fun addToSentMessages(message: String) = MapGuiNewChat.mapAddToSentMessages.invoke(this, message)
    fun printChatMessage(component: IChatComponent) = MapGuiNewChat.mapPrintChatMessage.invoke(this, component.original)

}