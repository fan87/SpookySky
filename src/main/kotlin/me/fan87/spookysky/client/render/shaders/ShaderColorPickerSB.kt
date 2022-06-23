package me.fan87.spookysky.client.render.shaders

import me.fan87.spookysky.client.render.ShaderProgram
import org.lwjgl.opengl.GL20

class ShaderColorPickerSB: ShaderProgram() {

    var hue: Float = 1.0f
    var width: Float = 1.0f
    var height: Float = 1.0f

    override fun loadShaderSource(): ShaderSourceBundle {
        return ShaderSourceBundle().fragmentShader.fromPath("shaders/colorpicker-sb.frag").end()
    }

    override fun updateUniform() {
        GL20.glUniform2f(getUniform("u_resolution"), width, height)
        GL20.glUniform1f(getUniform("u_hue"), hue)
    }

    override fun initUniform() {

    }
}