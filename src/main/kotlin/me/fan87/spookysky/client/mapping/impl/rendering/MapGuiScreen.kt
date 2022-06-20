package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*

object MapGuiScreen: ClassMapping<GuiScreen>() {
    override fun getWrapperClass(): Class<GuiScreen> {
        return GuiScreen::class.java
    }

    override val humanReadableName: String
        get() = "GuiScreen"


    val mapSendChatMessage = MethodMapping<Unit, GuiScreen>(this, "sendChatMessage(String,Boolean)")
    val mapHandleComponentClick = MethodMapping<Unit, GuiScreen>(this, "handleComponentClick(IChatComponent)")

}

open class GuiScreen protected constructor(original: Any): Gui(original) {

    fun sendChatMessage(msg: String, addToChat: Boolean) = MapGuiScreen.mapSendChatMessage.invoke(this, msg, addToChat)

}