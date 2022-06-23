package me.fan87.spookysky.client.module.impl.render

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.RenderEntityEvent
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.mapping.impl.rendering.Framebuffer
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.ColorSetting
import me.fan87.spookysky.client.render.RenderStateManager
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.EXTFramebufferObject
import org.lwjgl.opengl.EXTPackedDepthStencil
import org.lwjgl.opengl.GL11
import java.awt.Color

class Chams: Module("Chams", "See entities through walls", Category.RENDER) {

    val color = ColorSetting("Color", "The color of chams", Color(0xffffff))

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    @EventHandler
    fun onRenderEntity(event: RenderEntityEvent) {
        checkSetupFBO()
        if (event.entity == mc.thePlayer) {
            return
        }

        if (event.entity !is EntityLivingBase) return

        RenderStateManager.renderShadow = false
        RenderStateManager.renderNameTag = false

        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT)
        GL11.glClearStencil(0xff)
        GL11.glEnable(GL11.GL_STENCIL_TEST)
        GL11.glStencilMask(0xff)
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xff)
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE)

        mc.renderManager?.renderEntityStatic(event.entity, mc.timer.renderPartialTicks, true)

        GL11.glStencilMask(0x00)
        val healthColor = color.value
        GL11.glColor4f(healthColor.red / 255f, healthColor.green / 255f, healthColor.blue / 255f, healthColor.alpha / 255f)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xff)
        GL11.glPushMatrix()

        GL11.glTranslated(0.0, mc.thePlayer!!.getEyeHeight().toDouble(), 0.0)
        GL11.glRotated((-(mc.thePlayer!!.rotationYaw + 180)).toDouble(), 0.0, 1.0, 0.0)
        GL11.glRotated((-mc.thePlayer!!.rotationPitch).toDouble(), 1.0, 0.0, 0.0)
        GL11.glTranslated(0.0, 0.0, -0.1)
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glDisable(GL11.GL_CULL_FACE)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_LIGHTING)

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glBegin(GL11.GL_QUADS)
        GL11.glVertex2d(-10.0, -10.0)
        GL11.glVertex2d(10.0, -10.0)
        GL11.glVertex2d(10.0, 10.0)
        GL11.glVertex2d(-10.0, 10.0)
        GL11.glEnd()
        GL11.glPopAttrib()
        GL11.glPopAttrib()
        GL11.glPopMatrix()

        GL11.glColor4f(1f, 1f, 1f, 1f)
        GL11.glDisable(GL11.GL_BLEND)

        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xff)
        GL11.glStencilMask(0xff)
        GL11.glDisable(GL11.GL_STENCIL_TEST)

        RenderStateManager.renderShadow = true
        RenderStateManager.renderNameTag = true

    }

    fun checkSetupFBO() {
        val fbo = mc.framebufferMc

        if (fbo != null) {
            if (fbo.depthBuffer > -1) {
                setupFBO(fbo)
                fbo.depthBuffer = -1
            }
        }
    }

    fun setupFBO(fbo: Framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer)
        val stencilDepthBuffer = EXTFramebufferObject.glGenRenderbuffersEXT()
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBuffer)
        EXTFramebufferObject.glRenderbufferStorageEXT(
            EXTFramebufferObject.GL_RENDERBUFFER_EXT,
            EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT,
            Display.getWidth(),
            Display.getHeight()
        )
        EXTFramebufferObject.glFramebufferRenderbufferEXT(
            EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
            EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT,
            EXTFramebufferObject.GL_RENDERBUFFER_EXT,
            stencilDepthBuffer
        )
        EXTFramebufferObject.glFramebufferRenderbufferEXT(
            EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
            EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,
            EXTFramebufferObject.GL_RENDERBUFFER_EXT,
            stencilDepthBuffer
        )
    }

}