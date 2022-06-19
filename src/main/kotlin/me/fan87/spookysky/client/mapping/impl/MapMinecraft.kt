package me.fan87.spookysky.client.mapping.impl

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.entities.EntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.rendering.GuiScreen

object MapMinecraft: ClassMapping<Minecraft>() {
    override val humanReadableName: String
        get() = "Minecraft"

    val mapGetMinecraft = MethodMapping<Any, Minecraft>(this, "getMinecraft()")
    val mapSetIngameFocus = MethodMapping<Unit, Minecraft>(this, "setIngameFocus()")
    val mapDisplayGuiScreen = MethodMapping<Unit, Minecraft>(this, "displayGuiScreen()")
    val mapClickMouse = MethodMapping<Unit, Minecraft>(this, "clickMouse()")
    val mapRunTick = MethodMapping<Unit, Minecraft>(this, "runTick()")
    val mapRightClickMouse = MethodMapping<Unit, Minecraft>(this, "rightClickMouse()")

    val mapLeftClickCounter = FieldMapping<Int, Minecraft>(this, "leftClickCounter")
    val mapThePlayer = FieldMapping<Any, Minecraft>(this, "thePlayer")
    val mapDebugFPS = FieldMapping<Int, Minecraft>(this, "debugFPS")
}

class Minecraft(original: Any): WrapperClass(original) {
    companion object {
        fun getMinecraft(): Minecraft {
            return WrappedMethodType(MapMinecraft.mapGetMinecraft, Minecraft::class.java).invoke(null)!!
        }
        var debugFPS by MapMinecraft.mapDebugFPS
    }

    fun runTick() = MapMinecraft.mapClickMouse.invoke(this)
    fun clickMouse() = MapMinecraft.mapClickMouse.invoke(this)
    fun rightClickMouse() = MapMinecraft.mapRightClickMouse.invoke(this)
    fun setIngameFocus() = MapMinecraft.mapSetIngameFocus.invoke(this)
    fun displayGuiScreen(guiScreen: GuiScreen) = MapMinecraft.mapDisplayGuiScreen.invoke(this, guiScreen)

    var leftClickCounter by MapMinecraft.mapLeftClickCounter
    var thePlayer by WrappedFieldType(MapMinecraft.mapThePlayer, EntityPlayerSP::class.java)
}