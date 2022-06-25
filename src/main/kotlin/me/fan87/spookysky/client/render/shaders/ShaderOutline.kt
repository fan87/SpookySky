package me.fan87.spookysky.client.render.shaders

import me.fan87.spookysky.client.render.FramebufferShaderProgram
import org.lwjgl.opengl.GL20
import java.awt.Color

class ShaderOutline: FramebufferShaderProgram() {
    override fun loadShaderSource(): ShaderSourceBundle {
        return ShaderSourceBundle()
            .fragmentShader.fromPath("shaders/outline.frag").end()
    }

    var radius = 0.0005f
    var color = Color.WHITE

    private var currentDirection = 0

    override fun updateUniform() {
        GL20.glUniform4f(getUniform("u_color"), color.red/255f, color.green/255f, color.blue/255f, color.alpha/255f)
        GL20.glUniform1f(getUniform("u_radius"),  radius)
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