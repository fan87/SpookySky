package me.fan87.spookysky.client.mapping.impl

import me.fan87.spookysky.client.mapping.*

object MapMinecraft: ClassMapping<Minecraft>() {
    override val humanReadableName: String
        get() = "Minecraft"

    val mapGetMinecraft = MethodMapping<Any, Minecraft>(this, "getMinecraft()")
    val mapSetIngameFocus = MethodMapping<Unit, Minecraft>(this, "setIngameFocus()")
    val mapDisplayGuiScreen = MethodMapping<Unit, Minecraft>(this, "displayGuiScreen()")

    val mapLeftClickCounter = FieldMapping<Int, Minecraft>(this, "leftClickCounter")
}

class Minecraft(original: Any): WrapperClass(original) {
    companion object {
        fun getMinecraft(): Minecraft {
            return WrappedMethodType(MapMinecraft.mapGetMinecraft, Minecraft::class.java).invoke(null)!!
        }
    }

    fun setIngameFocus() = MapMinecraft.mapSetIngameFocus.invoke(this)
    fun displayGuiScreen(guiScreen: GuiScreen) = MapMinecraft.mapDisplayGuiScreen.invoke(this, guiScreen)

    var leftClickCounter by MapMinecraft.mapLeftClickCounter
}