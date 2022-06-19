package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*

object MapGuiIngame: ClassMapping<GuiIngame>() {
    override val humanReadableName: String
        get() = "GuiIngame"



}

class GuiIngame(original: Any): WrapperClass(original) {


}