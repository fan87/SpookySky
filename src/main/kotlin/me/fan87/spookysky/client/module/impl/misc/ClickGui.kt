package me.fan87.spookysky.client.module.impl.misc

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.*
import me.fan87.spookysky.client.mapping.impl.packets.play.client.C03PacketPlayer
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.utils.CFontRenderer
import me.fan87.spookysky.client.utils.RenderUtils
import me.fan87.spookysky.client.utils.Vector2d
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class ClickGui: Module("ClickGui", "A gui that allows you to manage every module in the client", Category.Misc) {
    var startYaw: Float = 0f

    var pitchOffset: Float = -10f
    var distance: Double = 1.0
    var scale: Float = 1.5f

    override fun onEnable() {
        this.pitchOffset = -20f
        this.distance = 1.0
        startYaw = mc.thePlayer?.rotationYaw?:0f
    }

    override fun onDisable() {

    }

    @EventHandler
    fun onLeftClick(event: ClickMouseEvent) {
        event.cancelled = true
        val mousePos = getMousePosition()
        mc.gameSettings!!.keyBindAttack.unpresskey()
        mc.gameSettings!!.keyBindAttack.unpresskey()
        onClick(0, mousePos.x, mousePos.y)
    }
    @EventHandler
    fun onRightClick(event: RightClickMouseEvent) {
        event.cancelled = true
        val mousePos = getMousePosition()
        onClick(1, mousePos.x, mousePos.y)
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

        GL11.glPushMatrix()
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glLineWidth(1f)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glRotated(-mc.thePlayer!!.rotationYaw % 360.0, 0.0, 1.0, 0.0)
        GL11.glRotated(mc.thePlayer!!.rotationPitch.toDouble(), 1.0, 0.0, 0.0)
        GL11.glBegin(GL11.GL_LINES)
        GL11.glVertex3d(0.0, 0.0, 0.0)
        GL11.glVertex3d(0.0, 0.0, 2.0)
        GL11.glEnd()
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glPopMatrix()


        GL11.glRotated(-startYaw % 360.0, 0.0, 1.0, 0.0)
        GL11.glRotated(-pitchOffset.toDouble(), 1.0, 0.0, 0.0)
        GL11.glScaled(1.5, 1.5, 1.0)
        GL11.glTranslated(-0.5, -0.5, distance)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_BLEND)
        render()
        GL11.glPopMatrix()
        GL11.glPopAttrib()
    }

    fun onClick(button: Int, posX: Double, posY: Double) {

    }

    fun render() {
        val regularFont = CFontRenderer.getFontRenderer("SourceCodePro-Light.ttf", 80)
        drawString(regularFont, "SpookySky", 1.5 - regularFont.getStringWidth("SpookySky")*0.001, 0.98, 0xffffffff)
        GL11.glTranslated(0.0, 0.0, 0.01)
        RenderUtils.drawRect(-0.3, 0.0, 1.3, 1.0, Color(0f, 0f, 0f, 0.6f).rgb)
        GL11.glTranslated(0.0, 0.0, -0.01)
        var mousePosition = getMousePosition()
        RenderUtils.drawRect(mousePosition.x-0.005, mousePosition.y-0.005, mousePosition.x+0.005, mousePosition.y+0.005, 0xffffffff.toInt())
    }

    fun getMousePosition(): Vector2d {
        val rotationYaw = (mc.thePlayer!!.rotationYaw - startYaw)
        val rotationPitch = mc.thePlayer!!.rotationPitch + pitchOffset
        val x = tan(Math.toRadians(-rotationYaw.toDouble())) * distance
        val yDistance = sqrt(x*x + distance*distance)
        val finalY = tan(Math.toRadians(-rotationPitch.toDouble())) * yDistance
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

}