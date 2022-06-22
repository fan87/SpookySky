package me.fan87.spookysky.client.module.impl.movement

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.events.events.WorldTickEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting
import me.fan87.spookysky.client.utils.MathUtils
import me.fan87.spookysky.client.utils.MathUtils.getDirection
import me.fan87.spookysky.client.utils.MathUtils.getMotion
import me.fan87.spookysky.client.utils.MathUtils.getRotatedVector2
import me.fan87.spookysky.client.utils.MathUtils.setMotion
import me.fan87.spookysky.client.utils.Vector2d
import me.fan87.spookysky.client.utils.Vector3d

class Speed: Module("Speed", "Makes you walk faster", Category.MOVEMENT) {

    val speed = DoubleSetting("Speed", "The speed you are going to be moving at (Unit: BPT)", 0.4, 0.1, 8.0)

    override fun onEnable() {

    }

    override fun onDisable() {
        mc.thePlayer?.setMotion(Vector3d(0.0, 0.0, 0.0))
    }

    @EventHandler
    fun onTick(event: WorldTickEvent) {
        if (mc.thePlayer!!.isMoving()) {
            if (mc.thePlayer!!.onGround) {
                mc.thePlayer!!.jump()
            }
            val value = MathUtils.getRotationVector2(mc.thePlayer!!.getDirection()) * speed.value.toFloat()
            mc.thePlayer!!.setMotion(value)
        } else {
            mc.thePlayer!!.setMotion(Vector2d(0.0, 0.0))

        }


    }

}