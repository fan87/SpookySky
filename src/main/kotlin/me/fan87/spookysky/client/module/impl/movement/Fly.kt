package me.fan87.spookysky.client.module.impl.movement

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting
import me.fan87.spookysky.client.utils.MathUtils
import me.fan87.spookysky.client.utils.MathUtils.addMotion
import me.fan87.spookysky.client.utils.MathUtils.setMotion

class Fly: Module("Fly", "Fly like a bird", Category.MOVEMENT) {

    val speed = DoubleSetting("Speed", "The flying speed. Unit: BPT", 0.1, 0.1, 8.9)

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
        player.motionX = 0.0
        player.motionY = 0.0
        player.motionZ = 0.0
        if (mc.gameSettings!!.keyBindJump.pressed) {
            player.motionY += speed.value
        }
        if (mc.gameSettings!!.keyBindForward.pressed) {
            val direction = MathUtils.getRotationVector2(player.rotationYaw)
            player.addMotion(direction.toVector2d()*speed.value)
        }
        if (mc.gameSettings!!.keyBindBack.pressed) {
            val direction = MathUtils.getRotationVector2(player.rotationYaw)
            player.addMotion(direction.toVector2d()*(-speed.value))
        }
        if (mc.gameSettings!!.keyBindRight.pressed) {
            val direction = MathUtils.getRotationVector2(player.rotationYaw + 90)
            player.addMotion(direction.toVector2d()*speed.value)
        }
        if (mc.gameSettings!!.keyBindLeft.pressed) {
            val direction = MathUtils.getRotationVector2(player.rotationYaw + 90)
            player.addMotion(direction.toVector2d()*(-speed.value))
        }
        if (mc.gameSettings!!.keyBindSneak.pressed) {
            player.motionY += -speed.value
        }
    }


}