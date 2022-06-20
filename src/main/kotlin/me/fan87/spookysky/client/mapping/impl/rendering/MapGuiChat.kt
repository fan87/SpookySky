package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.chat.IChatComponent

object MapGuiChat: ClassMapping<GuiChat>() {
    override fun getWrapperClass(): Class<GuiChat> {
        return GuiChat::class.java
    }

    override val humanReadableName: String
        get() = "GuiChat"

    val mapOnAutocompleteResponse = MethodMapping<Unit, GuiChat>(this, "onAutocompleteResponse(String[])")
    val mapSendAutocompleteRequest = MethodMapping<Unit, GuiChat>(this, "sendAutocompleteRequest(String,String)")
    val mapWaitingOnAutocomplete = FieldMapping<Boolean, GuiChat>(this, "waitingOnAutocomplete")

}

class GuiChat protected constructor(original: Any): GuiScreen(original) {

    fun onAutocompleteResponse(result: Array<String>) = MapGuiChat.mapOnAutocompleteResponse.invoke(this, result)
    fun sendAutocompleteRequest(beforeCursor: String, p_146405_2_: String) = MapGuiChat.mapSendAutocompleteRequest.invoke(this, beforeCursor, p_146405_2_)

    var waitingOnAutocomplete by MapGuiChat.mapWaitingOnAutocomplete

}