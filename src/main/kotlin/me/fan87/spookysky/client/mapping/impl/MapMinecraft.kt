package me.fan87.spookysky.client.mapping.impl

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.entities.EntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.rendering.GuiIngame
import me.fan87.spookysky.client.mapping.impl.rendering.GuiScreen
import me.fan87.spookysky.client.mapping.impl.world.WorldClient

object MapMinecraft: ClassMapping<Minecraft>() {
    override fun getWrapperClass(): Class<Minecraft> {
        return Minecraft::class.java
    }

    override val humanReadableName: String
        get() = "Minecraft"

    val mapGetMinecraft = MethodMapping<Any, Minecraft>(this, "getMinecraft()")
    val mapSetIngameFocus = MethodMapping<Unit, Minecraft>(this, "setIngameFocus()")
    val mapDisplayGuiScreen = MethodMapping<Unit, Minecraft>(this, "displayGuiScreen(GuiScreen)")
    val mapClickMouse = MethodMapping<Unit, Minecraft>(this, "clickMouse()")
    val mapRunTick = MethodMapping<Unit, Minecraft>(this, "runTick()")
    val mapRightClickMouse = MethodMapping<Unit, Minecraft>(this, "rightClickMouse()")

    val mapLeftClickCounter = FieldMapping<Int, Minecraft>(this, "leftClickCounter")
    val mapThePlayer = NullableFieldMapping<Any, Minecraft>(this, "thePlayer")
    val mapDebugFPS = FieldMapping<Int, Minecraft>(this, "debugFPS")
    val mapCurrentScreen = NullableFieldMapping<Any, Minecraft>(this, "currentScreen")
    val mapIngameGui = NullableFieldMapping<Any, Minecraft>(this, "ingameGui")
    val mapTheWorld = NullableFieldMapping<Any, Minecraft>(this, "theWorld")
}

class Minecraft protected constructor(original: Any): WrapperClass(original) {
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
    var thePlayer by NullableWrappedFieldType(MapMinecraft.mapThePlayer, EntityPlayerSP::class.java)
    var currentScreen by NullableWrappedFieldType(MapMinecraft.mapCurrentScreen, GuiScreen::class.java)
    var ingameGui by NullableWrappedFieldType(MapMinecraft.mapIngameGui, GuiIngame::class.java)
    var theWorld by NullableWrappedFieldType(MapMinecraft.mapTheWorld, WorldClient::class.java)
}