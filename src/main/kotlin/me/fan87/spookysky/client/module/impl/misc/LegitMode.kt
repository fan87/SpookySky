package me.fan87.spookysky.client.module.impl.misc

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.ModuleToggleEvent
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module

class LegitMode: Module("LegitMode", "Only allow legit modules", Category.MISC, true) {
    override fun onEnable() {
        for (module in spookySky.modulesManager.modules) {
            if (!module.legit && module.toggled) {
                module.toggled = false
            }
        }
    }

    override fun onDisable() {

    }

    @EventHandler
    fun onToggle(event: ModuleToggleEvent) {
        if (!event.module.legit && event.newValue) {
            event.cancelled = true
        }
    }

}