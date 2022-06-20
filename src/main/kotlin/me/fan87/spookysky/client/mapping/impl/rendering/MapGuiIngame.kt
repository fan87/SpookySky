package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*

object MapGuiIngame: ClassMapping<GuiIngame>() {
    override fun getWrapperClass(): Class<GuiIngame> {
        return GuiIngame::class.java
    }

    override val humanReadableName: String
        get() = "GuiIngame"

    val mapGetChatGUI = MethodMapping<Any, GuiIngame>(this, "getChatGUI()")

}

class GuiIngame protected constructor(original: Any): Gui(original) {

    fun getChatGUI(): GuiNewChat = MappingsManager.getWrapped(MapGuiIngame.mapGetChatGUI.invoke(this)!!)

}