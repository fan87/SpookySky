package me.fan87.spookysky.client.module.impl.render

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PostRender3DEvent
import me.fan87.spookysky.client.events.events.PostRenderEntityModelEvent
import me.fan87.spookysky.client.events.events.RenderEntityModelEvent
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.render.FramebufferShaderProgram
import me.fan87.spookysky.client.render.RenderStateManager
import me.fan87.spookysky.client.render.ShaderProgram
import me.fan87.spookysky.client.render.Shaders
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20

class Outline: Module("Outline", "Shows outline of entities", Category.RENDER) {


    override fun onEnable() {
    }

    override fun onDisable() {
    }


    @EventHandler
    fun onRender(event: RenderEntityModelEvent) {
    }

    @EventHandler
    fun onRender(event: PostRender3DEvent) {
        val partialTick = mc.timer.renderPartialTicks
        glPushAttrib(GL_ALL_ATTRIB_BITS)
        glPushMatrix()
        val shader = Shaders.outline
        RenderStateManager.renderShadow = false
        Minecraft.getMinecraft().entityRenderer!!.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0)
        shader.startUsing()
        RenderStateManager.renderShadow = false

        for (entity in mc.theWorld!!.loadedEntityList) {
            if (entity == mc.thePlayer) {
                continue
            }
            if (entity !is EntityLivingBase) {
                continue
            }

            glDisable(GL_LIGHTING)
            mc.renderManager!!.renderEntitySimple(entity, partialTick)
            glEnable(GL_LIGHTING)

        }

        RenderStateManager.renderShadow = true
        Minecraft.getMinecraft().entityRenderer!!.setupOverlayRendering()
        shader.stopUsing()
        RenderStateManager.renderShadow = true
        Minecraft.getMinecraft().entityRenderer!!.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0)
        glPopMatrix()
        glPopAttrib()
        GL13.glActiveTexture(33985)
        glDisable(GL_TEXTURE_2D)
        GL13.glActiveTexture(33984)
    }


}

