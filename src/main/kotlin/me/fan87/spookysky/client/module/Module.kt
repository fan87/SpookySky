package me.fan87.spookysky.client.module

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.Mapping

abstract class Module(val name: String, val description: String, val category: Category) {

    var toggled = false
        set(value) {
            if (value != field) {
                if (value) {
                    if (dependencies.any { !it.isMapped() }) {
                        SpookySky.debug("[Module] [$name] Dependency\"${dependencies.first { !it.isMapped() }.humanReadableName}\" has not been solved yet, could not enable the module.")
                        return
                    }
                    onEnable()
                } else {
                    onDisable()
                }
                field = value
            }
        }

    val spookySky: SpookySky
        get() = SpookySky.INSTANCE

    private val dependencies = ArrayList<Mapping<*>>()
    fun dependsOn(map: Mapping<*>) {
        dependencies.add(map)
    }

    protected abstract fun onEnable()
    protected abstract fun onDisable()

}