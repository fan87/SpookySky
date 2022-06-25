package me.fan87.spookysky.client.module.impl.combat

import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.mapping.impl.entities.EntityPlayer
import me.fan87.spookysky.client.mapping.impl.entities.EntityPlayerSP
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.TargetSelector
import me.fan87.spookysky.client.module.settings.impl.BooleanSetting

class Target: Module("Target", "Override targets", Category.COMBAT) {

    val targetSelector = TargetSelector(this)

    override fun onEnable() {

    }

    override fun onDisable() {

    }

}