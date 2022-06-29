package me.fan87.spookysky.client.module.impl.render

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.RenderEntityEvent
import me.fan87.spookysky.client.events.events.RenderEntityModelEvent
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.mapping.impl.rendering.Framebuffer
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.TargetSelector
import me.fan87.spookysky.client.module.settings.impl.ColorSetting
import me.fan87.spookysky.client.render.RenderStateManager
import me.fan87.spookysky.client.utils.RenderUtils
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.EXTFramebufferObject
import org.lwjgl.opengl.EXTPackedDepthStencil
import org.lwjgl.opengl.GL11
import java.awt.Color

class Chams: Module("Chams", "See entities through walls", Category.RENDER) {

    val color = ColorSetting("Color", "The color of chams", Color(0xffffff))

    val targetSelector = TargetSelector(this)

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    @EventHandler
    fun onRenderEntity(event: RenderEntityModelEvent) {
        if (!targetSelector.matches(event.entity)) {
            return
        }
        RenderUtils.confirmSetupStencilAttachment()

        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT)
        GL11.glClearStencil(0xff)
        GL11.glEnable(GL11.GL_STENCIL_TEST)
        GL11.glStencilMask(0xff)
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xff)
        GL11.glDepthMask(false)
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE)

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
        GL11.glDepthMask(true)

    }




}