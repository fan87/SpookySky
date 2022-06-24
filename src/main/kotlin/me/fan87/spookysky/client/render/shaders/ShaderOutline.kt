package me.fan87.spookysky.client.render.shaders

import me.fan87.spookysky.client.render.FramebufferShaderProgram
import org.lwjgl.opengl.GL20

class ShaderOutline: FramebufferShaderProgram() {
    override fun loadShaderSource(): ShaderSourceBundle {
        return ShaderSourceBundle()
            .fragmentShader.fromPath("shaders/outline.frag").end()
    }

    var currentDirection = 0

    override fun updateUniform() {
        GL20.glUniform1i(getUniform("u_texture"), 0)
        GL20.glUniform1i(getUniform("u_direction"), currentDirection)
    }

    override fun initUniform() {

    }

    override fun stopUsing() {
        currentDirection = 0
        super.stopUsing()
    }
}