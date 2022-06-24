package me.fan87.spookysky.client.module.impl.misc

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.RenderEntityModelEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.*
import me.fan87.spookysky.client.render.RenderStateManager
import me.fan87.spookysky.client.utils.RenderUtils
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.util.EnumSet

class Test: Module("Test", "A module to test stuff", Category.MOVEMENT) {

    val colorWithoutAlpha = ColorSetting("No Alpha", "Description Here", Color.RED, false)
    val color = ColorSetting("Color", "Description Here", Color.RED, true)
    val booleanSetting = BooleanSetting("Boolean", "Description Here", false)
    val enumSetting = EnumSetting("Enum A", "Description Here", Values.VALUE_B, Values::class)
    val enumStringSetting = StringEnumSetting("Enum B", "Description Here", "Value C", "Value A", "Value B", "Value C", "Value D")
    val intSetting = IntSetting("Int", "Description Here", 8, 5, 20)
    val doubleSetting = DoubleSetting("Double", "Description Here", 8.0, 5.0, 20.0)
    val keySetting = KeySetting("Key", "Description Here", Keyboard.KEY_N)


    override fun onEnable() {

    }

    override fun onDisable() {

    }

    @EventHandler
    fun onPreRenderModel(event: RenderEntityModelEvent) {


    }

    enum class Values {
        VALUE_A,
        VALUE_B,
        VALUE_C,
        VALUE_D
    }
}