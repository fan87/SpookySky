package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.Minecraft

object MapGuiScreen: ClassMapping<Minecraft>() {
    override val humanReadableName: String
        get() = "GuiScreen"

}

class GuiScreen(original: Any): WrapperClass(original) {

}