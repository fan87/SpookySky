package me.fan87.spookysky.client.module.impl.movement

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.events.events.WorldTickEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.utils.MathUtils.getMotion
import me.fan87.spookysky.client.utils.MathUtils.getRotatedVector2
import me.fan87.spookysky.client.utils.MathUtils.setMotion
import me.fan87.spookysky.client.utils.Vector3d

class Speed: Module("Speed", "Makes you walk faster", Category.MOVEMENT) {
    override fun onEnable() {

    }

    override fun onDisable() {
        mc.thePlayer?.setMotion(Vector3d(0.0, 0.0, 0.0))
    }

    @EventHandler
    fun onTick(event: WorldTickEvent) {
        mc.thePlayer!!.setMotion(mc.thePlayer!!.getRotatedVector2().toVector3f().toVector3d())
    }

}