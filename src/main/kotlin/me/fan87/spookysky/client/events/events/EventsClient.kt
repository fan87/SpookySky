package me.fan87.spookysky.client.events.events

import me.fan87.spookysky.client.module.Module

class ModuleToggleEvent(val module: Module, val oldValue: Boolean) {
    var cancelled = false
    val newValue = !oldValue
}