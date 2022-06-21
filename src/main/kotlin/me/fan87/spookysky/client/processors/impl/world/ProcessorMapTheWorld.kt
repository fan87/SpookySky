package me.fan87.spookysky.client.processors.impl.world

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.world.MapWorldClient
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapTheWorld: Processor("Map theWorld") {

    init {
        dependsOn(MapWorldClient)
        dependsOn(MapMinecraft)
    }

    override fun start() {
        onlyProcess(MapMinecraft)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (field in clazz.node.fields) {
            if (field.desc == MapWorldClient.assumeMapped().getDescName()) {
                MapMinecraft.mapTheWorld.map(field)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.mapTheWorld.isMapped()
    }
}