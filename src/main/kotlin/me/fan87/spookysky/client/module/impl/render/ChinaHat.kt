package me.fan87.spookysky.client.module.impl.render

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PostRender3DEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.ColorSetting
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.cos
import kotlin.math.sin

class ChinaHat: Module("ChinaHat", "Module name.", Category.RENDER) {

    val color = ColorSetting("Color", "Color of the china hat", Color(0x70ffd152, true))
    val height = DoubleSetting("Y Pos", "The display height of the china hat", 2.0, 1.5, 4.0)

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    @EventHandler
    fun onRender(event: PostRender3DEvent) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glShadeModel(GL11.GL_SMOOTH)
        GL11.glDisable(GL11.GL_CULL_FACE)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glColor4f(color.value.red.toFloat() / 255f, color.value.green.toFloat() / 255f, color.value.blue.toFloat() / 255f, color.value.alpha.toFloat() / 255f)

        GL11.glBegin(GL11.GL_TRIANGLE_STRIP)
        for (i in 0..360 step 10) {
            GL11.glVertex3d(0.0, height.value +  0.6, 0.0)
            GL11.glVertex3d(0- sin(Math.toRadians(i.toDouble())), height.value + 0.1, cos(Math.toRadians(i.toDouble())))
        }
        GL11.glEnd()

        GL11.glBegin(GL11.GL_LINE_STRIP)
        for (i in 0..360 step 10) {
            GL11.glVertex3d(sin(Math.toRadians(i.toDouble())), height.value + 0.1, cos(Math.toRadians(i.toDouble())))
        }
        GL11.glEnd()

        GL11.glPopAttrib()
    }
}