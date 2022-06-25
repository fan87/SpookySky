package me.fan87.spookysky.client.module.impl.combat

import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting

class Reach: Module("Reach", "Allows you to reach stuff from a longer distance", Category.COMBAT, true) {

    val combat = DoubleSetting("Combat", "The distance you can hit other entities", 3.5, 3.0, 4.5)

    override fun onEnable() {

    }

    override fun onDisable() {

    }



}