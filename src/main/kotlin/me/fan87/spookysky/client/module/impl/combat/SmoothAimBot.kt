package me.fan87.spookysky.client.module.impl.combat

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PostRender3DEvent
import me.fan87.spookysky.client.events.events.WorldTickEvent
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting
import me.fan87.spookysky.client.module.settings.impl.IntSetting
import me.fan87.spookysky.client.utils.MathUtils
import me.fan87.spookysky.client.utils.MathUtils.getDistanceToEntity
import me.fan87.spookysky.client.utils.MathUtils.getPosition
import java.lang.Math.*
import java.util.*

class SmoothAimBot: Module("SmoothAimBot", "Automatically aims to your target", Category.COMBAT, true) {

    val turnSpeed = IntSetting("Speed", "Turning speed of the bot", 30, 1, 100)
    val range = DoubleSetting("Range", "Range of the entity detection", 4.0, 2.0, 20.0)
    val fov = DoubleSetting("Fov", "Range of the entity detection", 180.0, 1.0, 360.0)

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    @EventHandler
    fun onTick(event: WorldTickEvent) {

    }

    @EventHandler
    fun onRender(event: PostRender3DEvent) {
        for (entity in mc.theWorld!!.loadedEntityList) {
            if (spookySky.modulesManager.getModule<Target>().metRequirements(entity)) {
                if (mc.thePlayer!!.getDistanceToEntity(entity) < range.value && mc.thePlayer!!.getDistanceToEntity(entity) > 0.5) {
                    var yaw: Float = MathUtils.tryFace(mc.thePlayer!!.getPosition(), entity.getPosition()).x
                    if (yaw > 180) {
                        yaw -= 360f
                    }
                    if (yaw < -180) {
                        yaw += 360f
                    }
                    val rotationYaw = mc.thePlayer!!.rotationYaw
                    val yawDiff = ((yaw - rotationYaw) % 360f + 540f) % 360f - 180f
                    if (abs(yawDiff) > fov.value / 2f) {
                        continue
                    }
                    mc.thePlayer!!.rotationYaw += yawDiff * (turnSpeed.value / 100f) * (20f / 120f)
                    return
                }
            }
        }
    }
}