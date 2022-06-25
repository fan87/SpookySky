package me.fan87.spookysky.client.module.impl.combat

import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting

class Reach: Module("Reach", "Allows you to reach stuff from a longer distance", Category.COMBAT, true) {

    val combat = DoubleSetting("Combat", "The distance you can hit other entities", 4.0, 1.0, 8.0)

    override fun onEnable() {

    }

    override fun onDisable() {

    }



}