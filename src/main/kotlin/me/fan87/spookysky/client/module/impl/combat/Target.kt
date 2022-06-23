package me.fan87.spookysky.client.module.impl.combat

import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.mapping.impl.entities.EntityPlayer
import me.fan87.spookysky.client.mapping.impl.entities.EntityPlayerSP
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.BooleanSetting

class Target: Module("Target", "Select your target types", Category.COMBAT) {

    val player = BooleanSetting("Player", "Attacks players", true)
    val mobs = BooleanSetting("Mobs", "Attacks mobs", false)

    override fun onEnable() {
        toggled = false
    }

    override fun onDisable() {

    }

    fun metRequirements(entity: Entity): Boolean {
        if (entity == mc.thePlayer) {
            return false
        }
        if (player.value) {
            if (entity is EntityPlayer) {
                return true
            }
        }
        if (mobs.value) {
            if (entity is EntityLivingBase && entity !is EntityPlayer) {
                return true
            }
        }
        return false
    }

}