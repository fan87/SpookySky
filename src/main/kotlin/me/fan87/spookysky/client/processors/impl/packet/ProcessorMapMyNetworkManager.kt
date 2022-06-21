package me.fan87.spookysky.client.processors.impl.packet

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.packets.MapNetworkManager
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapMyNetworkManager: Processor("Map myNetworkManager") {

    init {
        dependsOn(MapMinecraft)
        dependsOn(MapNetworkManager)
    }

    override fun start() {
        onlyProcess(MapMinecraft)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (field in clazz.node.fields) {
            if (field.desc == MapNetworkManager.assumeMapped().getDescName()) {
                MapMinecraft.mapMyNetworkManager.map(field)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.mapMyNetworkManager.isMapped()
    }
}