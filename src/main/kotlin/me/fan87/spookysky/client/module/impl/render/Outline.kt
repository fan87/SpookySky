package me.fan87.spookysky.client.module.impl.render

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PostRender3DEvent
import me.fan87.spookysky.client.events.events.PostRenderEntityModelEvent
import me.fan87.spookysky.client.events.events.RenderEntityModelEvent
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.TargetSelector
import me.fan87.spookysky.client.module.settings.impl.ColorSetting
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting
import me.fan87.spookysky.client.render.FramebufferShaderProgram
import me.fan87.spookysky.client.render.RenderStateManager
import me.fan87.spookysky.client.render.ShaderProgram
import me.fan87.spookysky.client.render.Shaders
import me.fan87.spookysky.client.utils.MathUtils.distanceTo
import me.fan87.spookysky.client.utils.MathUtils.getDistanceToEntity
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import java.awt.Color

class Outline: Module("Outline", "Shows outline of entities", Category.RENDER) {

    val maxDistance = DoubleSetting("Distance", "Maximum distance of outline being rendered", 100.0, 30.0, 130.0)
    val radius = DoubleSetting("Width", "Width of the outline", 5.0, 1.0, 100.0)
    val color = ColorSetting("Color", "Color of the outline", Color.WHITE)
    val targetSelector = TargetSelector(this)

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
        shader.radius = (radius.value * 0.0001).toFloat()
        shader.color = color.value
        RenderStateManager.renderShadow = false
        Minecraft.getMinecraft().entityRenderer!!.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0)
        shader.startUsing()
        RenderStateManager.renderShadow = false

        val player = mc.thePlayer!!
        for (entity in mc.theWorld!!.loadedEntityList.filter(targetSelector::matches).filter { it.getDistanceToEntity(player) <= maxDistance.value }) {

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

