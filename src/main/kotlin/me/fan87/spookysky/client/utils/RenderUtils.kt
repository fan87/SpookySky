package me.fan87.spookysky.client.utils

import me.fan87.spookysky.client.mapping.impl.rendering.Gui
import org.lwjgl.opengl.GL11

object RenderUtils {



    fun drawGradient(left: Int, top: Int, right: Int, bottom: Int, startColor: Long, endColor: Long) {
        Gui().drawGradient(left, top, right, bottom, startColor, endColor)
    }

    fun drawRect(left: Double, top: Double, right: Double, bottom: Double, color: Int) {
        var left = left
        var top = top
        var right = right
        var bottom = bottom
        if (left < right) {
            val i: Double = left
            left = right
            right = i
        }

        if (top < bottom) {
            val j: Double = top
            top = bottom
            bottom = j
        }
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        val f3 = (color shr 24 and 255).toFloat() / 255.0f
        val f = (color shr 16 and 255).toFloat() / 255.0f
        val f1 = (color shr 8 and 255).toFloat() / 255.0f
        val f2 = (color shr 0 and 255).toFloat() / 255.0f
        GL11.glColor4f(f, f1, f2, f3)
        GL11.glBegin(GL11.GL_QUADS)
        GL11.glVertex2d(left, bottom)
        GL11.glVertex2d(right, bottom)
        GL11.glVertex2d(right, top)
        GL11.glVertex2d(left, top)
        GL11.glEnd()
        GL11.glPopAttrib()
    }

}