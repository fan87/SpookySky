package me.fan87.spookysky.client.processors.impl

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.settings.MapGameSettings
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapGameSettingsField: Processor("Map mc.gameSettings") {

    init {
        dependsOn(MapMinecraft)
        dependsOn(MapGameSettings)
    }

    override fun start() {
        onlyProcess(MapMinecraft)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (field in clazz.node.fields) {
            if (field.desc == MapGameSettings.assumeMapped().getDescName()) {
                MapMinecraft.mapGameSettings.map(field)
                return false
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.mapGameSettings.isMapped()
    }
}