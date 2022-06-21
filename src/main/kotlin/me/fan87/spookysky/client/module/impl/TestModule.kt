package me.fan87.spookysky.client.module.impl

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.entities.MapEntity
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.utils.MathUtils.getRotatedVector3
import me.fan87.spookysky.client.utils.MathUtils.setMotion

class TestModule: Module("Test", "A module to test the module system", Category.SPOOKYSKY) {

    init {
        dependsOn(MapMinecraft)
        dependsOn(MapMinecraft.mapThePlayer)
        dependsOn(MapEntity.mapMotionX)
        dependsOn(MapEntity.mapMotionY)
        dependsOn(MapEntity.mapMotionZ)
        dependsOn(MapEntity.mapRotationPitch)
        dependsOn(MapEntity.mapRotationYaw)
        dependsOn(MapEntityPlayerSP)
    }

    override fun onEnable() {
        println("Test Module has been enabled")
        for (entity in mc.theWorld!!.loadedEntityList) {
            println("Entity: ${entity.getName()}")
        }
    }

    override fun onDisable() {
        println("Test Module has been disabled")
    }

    @EventHandler
    fun onTick(event: ClientTickEvent) {
        val player = mc.thePlayer
        if (player == null) {
            return
        }
        player.setMotion((player.getRotatedVector3() * 0.4F).toVector3d())

    }
}