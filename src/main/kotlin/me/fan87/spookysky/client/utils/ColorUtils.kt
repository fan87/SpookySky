package me.fan87.spookysky.client.utils

import java.awt.Color

object ColorUtils {

    @JvmStatic
    fun getChatRainbowR(): Float {
        return getChatRainbow().red / 255f
    }

    @JvmStatic
    fun getChatRainbowG(): Float {
        return getChatRainbow().green / 255f
    }

    @JvmStatic
    fun getChatRainbowB(): Float {
        return getChatRainbow().blue / 255f
    }

    @JvmStatic
    fun getChatRainbowI(): Int {
        return getRainbowColor(5000, 0.6f, 1f).rgb
    }

    fun getChatRainbow(): Color {
        return getRainbowColor(5000, 0.65f, 1f)
    }

    fun getRainbowHue(loopDuration: Long): Float {
        return ((System.currentTimeMillis() % loopDuration)*1.0 / loopDuration).toFloat()
    }

    fun getRainbowColor(loopDuration: Long, s: Float, b: Float): Color {
        return Color.getHSBColor(getRainbowHue(loopDuration), s, b)
    }

}