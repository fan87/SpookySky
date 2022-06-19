package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*

object MapGuiIngame: ClassMapping<GuiIngame>() {
    override val humanReadableName: String
        get() = "GuiIngame"

    val mapGetChatGUI = MethodMapping<Any, GuiIngame>(this, "getChatGUI()")

}

class GuiIngame(original: Any): WrapperClass(original) {

    fun getChatGUI(): GuiNewChat = GuiNewChat(MapGuiIngame.mapGetChatGUI.invoke(this)!!)

}