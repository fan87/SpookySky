package me.fan87.spookysky.client.module.settings

import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.mapping.impl.entities.EntityPlayer
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.BooleanSetting

class TargetSelector(val module: Module) {

    val mobs = BooleanSetting("Mobs", "Allow every entity", false)
    val player = BooleanSetting("Player", "Allow every player", true)

    init {
        module.settings.add(mobs)
        module.settings.add(player)
    }

    fun matches(entity: Entity): Boolean {
        if (module !is me.fan87.spookysky.client.module.impl.combat.Target) {
            val module1 =
                module.spookySky.modulesManager.getModule<me.fan87.spookysky.client.module.impl.combat.Target>()
            if (module1.toggled) {
                return module1.targetSelector.matches(entity)
            }
        }
        if (entity == Minecraft.getMinecraft().thePlayer) {
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