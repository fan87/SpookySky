package me.fan87.spookysky.client.events

import me.fan87.spookysky.client.module.Module

class ModuleToggleEvent(val module: Module, val oldValue: Boolean, val newValue: Boolean, var cancelled: Boolean = false)