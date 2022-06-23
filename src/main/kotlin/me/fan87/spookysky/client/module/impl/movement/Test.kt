package me.fan87.spookysky.client.module.impl.movement

import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module

class Test: Module("Test", "A module to test stuff", Category.MOVEMENT) {
    override fun onEnable() {
        mc.thePlayer!!.capabilities.allowFlying = true
    }

    override fun onDisable() {
        mc.thePlayer!!.capabilities.allowFlying = false
    }
}