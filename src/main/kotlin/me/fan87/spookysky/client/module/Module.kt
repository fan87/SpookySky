package me.fan87.spookysky.client.module

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.events.ModuleToggleEvent
import me.fan87.spookysky.client.mapping.Mapping
import me.fan87.spookysky.client.mapping.impl.Minecraft

abstract class Module(val name: String, val description: String, val category: Category) {

    var toggled = false
        set(value) {
            if (value != field) {
                if (value) {
                    if (dependencies.any { !it.isMapped() }) {
                        SpookySky.debug("[Module] [$name] Dependency\"${dependencies.first { !it.isMapped() }.humanReadableName}\" has not been solved yet, could not enable the module.")
                        return
                    }
                    val event = ModuleToggleEvent(this, false)
                    SpookySky.INSTANCE.eventManager.post(event)
                    if (event.cancelled) {
                        return
                    }
                    spookySky.eventManager.registerListener(this)
                    onEnable()
                } else {
                    val event = ModuleToggleEvent(this, true)
                    SpookySky.INSTANCE.eventManager.post(event)
                    if (event.cancelled) {
                        return
                    }
                    spookySky.eventManager.unregisterListener(this)
                    onDisable()
                }
                field = value
            }
        }

    val spookySky: SpookySky
        get() = SpookySky.INSTANCE

    val mc: Minecraft
        get() = Minecraft.getMinecraft()

    private val dependencies = ArrayList<Mapping<*>>()
    fun dependsOn(map: Mapping<*>) {
        dependencies.add(map)
    }

    protected abstract fun onEnable()
    protected abstract fun onDisable()



}