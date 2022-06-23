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
        RenderUtils.confirmSetupStencilAttachment()

        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT)
        GL11.glClearStencil(0xff)
        GL11.glEnable(GL11.GL_STENCIL_TEST)
        GL11.glStencilMask(0xff)
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xff)
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_KEEP)

        event.renderModel()

        GL11.glStencilMask(0x00)
        val healthColor = color.value
        GL11.glColor4f(healthColor.red / 255f, healthColor.green / 255f, healthColor.blue / 255f, healthColor.alpha / 255f)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glStencilFunc(GL11.GL_GEQUAL, 1, 0xff)

        GL11.glPushMatrix()
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)


        GL11.glLoadIdentity();

        GL11.glDisable(GL11.GL_CULL_FACE)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_LIGHTING)

        mc.entityRenderer!!.orientCamera(mc.timer.renderPartialTicks)

        GL11.glRotated((-(mc.thePlayer!!.rotationYaw + 180)).toDouble(), 0.0, 1.0, 0.0)
        GL11.glRotated((-mc.thePlayer!!.rotationPitch).toDouble(), 1.0, 0.0, 0.0)
        GL11.glTranslated(0.0, mc.thePlayer!!.getEyeHeight().toDouble(), -1.0)
        GL11.glColor4f(healthColor.red / 255f, healthColor.green / 255f, healthColor.blue / 255f, healthColor.alpha / 255f)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GL11.glBegin(GL11.GL_QUADS)
        GL11.glVertex2d(-10.0, -10.0)
        GL11.glVertex2d(10.0, -10.0)
        GL11.glVertex2d(10.0, 10.0)
        GL11.glVertex2d(-10.0, 10.0)
        GL11.glEnd()

        GL11.glPopAttrib()
        GL11.glPopMatrix()
        GL11.glColor4f(1f, 1f, 1f, 1f)
        GL11.glDisable(GL11.GL_BLEND)

        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xff)
        GL11.glStencilMask(0xff)
        GL11.glDisable(GL11.GL_STENCIL_TEST)

    }

    enum class Values {
        VALUE_A,
        VALUE_B,
        VALUE_C,
        VALUE_D
    }
}