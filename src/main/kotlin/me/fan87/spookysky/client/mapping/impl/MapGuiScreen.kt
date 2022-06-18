package me.fan87.spookysky.client.mapping.impl

import me.fan87.spookysky.client.mapping.*

object MapGuiScreen: ClassMapping<Minecraft>() {
    override val humanReadableName: String
        get() = "GuiScreen"

}

class GuiScreen(original: Any): WrapperClass(original) {

}