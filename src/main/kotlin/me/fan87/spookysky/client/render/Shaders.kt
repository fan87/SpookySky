package me.fan87.spookysky.client.render

import me.fan87.spookysky.client.render.shaders.ShaderColorPickerAlpha
import me.fan87.spookysky.client.render.shaders.ShaderColorPickerHue
import me.fan87.spookysky.client.render.shaders.ShaderColorPickerSB
import me.fan87.spookysky.client.render.shaders.ShaderOutline

object Shaders {

    val colorPickerAlpha by lazy { ShaderColorPickerAlpha() }
    val colorPickerHue by lazy { ShaderColorPickerHue() }
    val colorPickerSB by lazy { ShaderColorPickerSB() }
    val outline by lazy { ShaderOutline() }

}