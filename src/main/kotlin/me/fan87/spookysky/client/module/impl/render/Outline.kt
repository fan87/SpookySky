package me.fan87.spookysky.client.module.impl.render

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PostRenderEntityModelEvent
import me.fan87.spookysky.client.events.events.RenderEntityModelEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.render.FramebufferShaderProgram
import me.fan87.spookysky.client.render.ShaderProgram
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20

class Outline: Module("Outline", "Shows outline of entities", Category.RENDER) {

    var shader: ShaderOutline? = null

    override fun onEnable() {
        if (shader == null) {
            shader = ShaderOutline()
        }
    }

    override fun onDisable() {
    }


    @EventHandler
    fun onRender(event: RenderEntityModelEvent) {
        shader!!.startUsing()
    }

    @EventHandler
    fun onRender(event: PostRenderEntityModelEvent) {
        shader!!.stopUsing()
    }


}

class ShaderOutline: FramebufferShaderProgram() {
    override fun loadShaderSource(): ShaderSourceBundle {
        return ShaderSourceBundle()
            .fragmentShader.fromPath("shaders/outline.frag").end()
    }

    override fun updateUniform() {
        GL20.glUniform1i(getUniform("u_texture"), 0)
    }

    override fun initUniform() {

    }

}