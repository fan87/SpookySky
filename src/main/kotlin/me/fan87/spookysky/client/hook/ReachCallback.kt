package me.fan87.spookysky.client.hook

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.module.impl.combat.Reach

object ReachCallback {

    @JvmStatic
    fun getReach(): Double {
        val module = SpookySky.INSTANCE.modulesManager.getModule<Reach>()
        return if (module.toggled) module.combat.value else 3.0;
    }


}