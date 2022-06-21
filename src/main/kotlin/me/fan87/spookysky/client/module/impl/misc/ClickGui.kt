package me.fan87.spookysky.client.module.impl.misc

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.*
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.utils.CFontRenderer
import me.fan87.spookysky.client.utils.RenderUtils
import me.fan87.spookysky.client.utils.Vector2d
import net.java.games.input.Component.Identifier.Key.*
import org.lwjgl.opengl.GL11
import us.ihmc.euclid.matrix.RotationMatrix
import java.awt.Color
import kotlin.math.*


class ClickGui: Module("ClickGui", "A gui that allows you to manage every module in the client", Category.Misc) {
    var startYaw: Float = 0f

    var pitchOffset: Float = 0f
    var distance: Double = 1.0
    var scale: Float = 2.0f

    override fun onEnable() {
        this.pitchOffset = 0f
        this.distance = 0.6
        this.scale = 1.5f;
        startYaw = mc.thePlayer?.rotationYaw?.plus(0f) ?:0f
    }

    override fun onDisable() {

    }

    @EventHandler
    fun onLeftClick(event: ClickMouseEvent) {
        val mousePos = getMousePosition()
        if (onClick(0, mousePos.x, mousePos.y)) {
            mc.gameSettings!!.keyBindAttack.unpresskey()
            event.cancelled = true
        }
    }
    @EventHandler
    fun onRightClick(event: RightClickMouseEvent) {
        val mousePos = getMousePosition()
        if (onClick(1, mousePos.x, mousePos.y)) {
            event.cancelled = true

        }
    }

    @EventHandler
    fun onTick(event: ClientTickEvent) {
        mc.gameSettings!!.keyBindAttack.unpresskey()
    }

    @EventHandler
    fun onRender(event: PostRender3DEvent) {
        val player = mc.thePlayer!!
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glPushMatrix()
        GL11.glLoadIdentity()
        mc.entityRenderer!!.orientCamera(event.partialTicks)
        GL11.glTranslated(0.0, player.getEyeHeight()!!.toDouble(), 0.0)


        GL11.glRotated(-startYaw % 360.0, 0.0, 1.0, 0.0)
        GL11.glRotated(-pitchOffset.toDouble(), 1.0, 0.0, 0.0)
        GL11.glScaled(scale*1.0, scale*1.0, 1.0)
        GL11.glTranslated(-0.5, -0.5, distance)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_BLEND)
        render()
        GL11.glPopMatrix()
        GL11.glPopAttrib()
    }

    var grabbing = false
    var grabYaw = 0f

    fun onClick(button: Int, posX: Double, posY: Double): Boolean {
        if (categoriesClick(button, posX, posY)) return true
        if (clickGrab()) return true
        return false
    }

    fun render() {
        val regularFont = CFontRenderer.getFontRenderer("Jura-Regular.ttf", 80)
        renderCategories()
        renderCursor()
        renderGrab()
    }

    var selectedCategory: Category = Category.COMBAT

    fun renderGrab() {
        RenderUtils.drawRect(0.7, 0.15, 0.4, 0.20, 0x70ffffff.toInt())
        val value = 0.05 / 5
        var current = value + 0.15
        GL11.glTranslated(0.0, 0.0, -0.001)
        for (i in 0 until 3) {
            RenderUtils.drawRect(0.6, current, 0.5, current + 0.004, 0xff7f7f7f.toInt())
            current += value
        }
        GL11.glTranslated(0.0, 0.0, 0.001)
        if (grabbing) {
            startYaw = mc.thePlayer!!.rotationYaw - grabYaw
        }
    }

    fun clickGrab(): Boolean {
        if (grabbing) {
            grabbing = false
            return true
        } else {
            if (isInSection(0.4, 0.15, 0.7, 0.20)) {
                grabbing = true
                grabYaw = mc.thePlayer!!.rotationYaw - startYaw
                return true
            }
        }
        return false
    }

    fun renderCategories() {
        for (value in Category.values().filter { it != Category.RENDER }.withIndex()) {
            var left = 0.65
            var top = 0.6 + value.index * -0.053
            var right = left + 0.05
            var bottom = top + 0.05
            RenderUtils.drawTexturedRect("clickgui/${if (value.value == selectedCategory) "selected" else "unselected"}/${value.value.displayName}.png", left, top, 1f, 1f, right, bottom, 0f, 0f)
        }
    }

    fun categoriesClick(button: Int, posX: Double, posY: Double): Boolean {
        for (value in Category.values().filter { it != Category.RENDER }.withIndex()) {
            var left = 0.65
            var top = 0.6 + value.index * -0.053
            var right = left + 0.05
            var bottom = top + 0.05
            if (isInSection(left, top, right, bottom)) {
                selectedCategory = value.value
                return true
            }
        }
        return false
    }


    fun renderCursor() {
        GL11.glTranslated(0.0, 0.0, -0.001)
        var mousePosition = getMousePosition()
        RenderUtils.drawRect(mousePosition.x-0.0025, mousePosition.y-0.0025, mousePosition.x+0.0025, mousePosition.y+0.0025, 0xffffffff.toInt())
    }

    fun getRelativeYawPitch(): Vector2d {
        val matrix = RotationMatrix(Math.toRadians(mc.thePlayer!!.rotationYaw.toDouble() - 180), Math.toRadians(mc.thePlayer!!.rotationPitch.toDouble()), 0.0)
        matrix.prependPitchRotation(Math.toRadians(-pitchOffset.toDouble()))
        matrix.prependYawRotation(Math.toRadians(-startYaw.toDouble()))
        return Vector2d(Math.toDegrees(matrix.yaw), Math.toDegrees(matrix.pitch))
    }

    fun getMousePosition(): Vector2d {
        val relativeYawPitch = getRelativeYawPitch()
        val rotationYaw = relativeYawPitch.x
        val rotationPitch = relativeYawPitch.y
        var x = tan(Math.toRadians(-rotationYaw)) * distance
        val yDistance = sqrt(x*x + distance*distance)
        var finalY = tan(Math.toRadians(-rotationPitch)) * yDistance
        return Vector2d(x/scale + 0.5, finalY/scale + 0.5)
    }

    fun drawString(font: CFontRenderer, string: String, x: Double, y: Double, color: Long) {
        GL11.glPushMatrix()
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glTranslated(x, y, 0.0)
        GL11.glScaled(0.001, 0.001, 1.0)
        GL11.glRotated(180.0, 0.0, 0.0, 1.0)
        GL11.glTranslated(0.0, 0.0, -0.01)
        font.drawString(string, 0f, 0f, color.toInt())
        GL11.glPopAttrib()
        GL11.glPopMatrix()

    }

    @EventHandler
    fun onCrossHair(event: CrossHairEvent) {
        event.cancelled = true
    }

    fun isInSection(startX: Double, startY: Double, endX: Double, endY: Double): Boolean {
        val mousePosition = getMousePosition()
        return mousePosition.x >= startX && mousePosition.x <= endX && mousePosition.y >= startY && mousePosition.y <= endY
    }

}