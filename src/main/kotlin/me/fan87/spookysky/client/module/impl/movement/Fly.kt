package me.fan87.spookysky.client.module.impl.movement

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting

class Fly: Module("Fly", "Fly like a bird", Category.MOVEMENT) {

    val speed = DoubleSetting("Speed", "The flying speed. Unit: BPT", 0.0, 0.0, 8.9)

    override fun onEnable() {

    }

    override fun onDisable() {
        val player = mc.thePlayer ?: return
        player.motionX = 0.0
        player.motionY = 0.0
        player.motionZ = 0.0
    }

    @EventHandler
    fun onTick(event: ClientTickEvent) {
        val player = mc.thePlayer ?: return
        if (mc.gameSettings!!.keyBindJump.pressed) {
            player.motionY += speed.value
        }
    }


}