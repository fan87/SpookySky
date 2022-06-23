package me.fan87.spookysky.client.render.shaders

import me.fan87.spookysky.client.render.ShaderProgram
import org.lwjgl.opengl.GL20

class ShaderColorPickerHue: ShaderProgram() {

    var width: Float = 1.0f
    var height: Float = 1.0f

    override fun loadShaderSource(): ShaderSourceBundle {
        return ShaderSourceBundle().fragmentShader.fromPath("shaders/colorpicker-hue.frag").end()
    }

    override fun updateUniform() {
        GL20.glUniform2f(getUniform("u_resolution"), width, height)
    }

    override fun initUniform() {

    }
}