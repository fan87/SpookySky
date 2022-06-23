package me.fan87.spookysky.client.render.shaders

import me.fan87.spookysky.client.render.ShaderProgram
import org.lwjgl.opengl.GL20

class ShaderColorPickerAlpha: ShaderProgram() {

    var red: Float = 1f
    var green: Float = 1f
    var blue: Float = 1f

    var width: Float = 1f
    var height: Float = 1f
    var alphaBoxSize: Float = 10f

    override fun loadShaderSource(): ShaderSourceBundle {
        return ShaderSourceBundle().fragmentShader.fromPath("shaders/colorpicker-alpha.frag").end()
    }

    override fun updateUniform() {
        GL20.glUniform3f(getUniform("u_color"), red, green, blue)
        GL20.glUniform2f(getUniform("u_resolution"), width, height)
        GL20.glUniform1f(getUniform("u_box_size"), alphaBoxSize)
    }

    override fun initUniform() {
    }
}