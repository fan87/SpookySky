package me.fan87.spookysky.client.module.impl.misc

import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting

class Timer: Module("Timer", "Makes your game runs faster or slower", Category.MISC) {

    val speed = DoubleSetting("Speed", "The speed multiplier of the game", 0.5, 0.1, 20.0)

    override fun onEnable() {
        mc.timer.timerSpeed = speed.value.toFloat()
    }

    override fun onDisable() {
        mc.timer.timerSpeed = 1f
    }
}