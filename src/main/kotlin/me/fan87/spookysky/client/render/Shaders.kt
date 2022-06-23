package me.fan87.spookysky.client.render

import me.fan87.spookysky.client.render.shaders.ShaderColorPickerAlpha
import me.fan87.spookysky.client.render.shaders.ShaderColorPickerHue
import me.fan87.spookysky.client.render.shaders.ShaderColorPickerSB

object Shaders {

    val colorPickerAlpha = ShaderColorPickerAlpha()
    val colorPickerHue = ShaderColorPickerHue()
    val colorPickerSB = ShaderColorPickerSB()

}