package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*

object MapGuiScreen: ClassMapping<GuiScreen>() {
    override val humanReadableName: String
        get() = "GuiScreen"


    val mapSendChatMessage = MethodMapping<Unit, GuiScreen>(this, "sendChatMessage(String,Boolean)")
    val mapHandleComponentClick = MethodMapping<Unit, GuiScreen>(this, "handleComponentClick(IChatComponent)")

}

class GuiScreen(original: Any): WrapperClass(original) {

    fun sendChatMessage(msg: String, addToChat: Boolean) = MapGuiScreen.mapSendChatMessage.invoke(this, msg, addToChat)

}