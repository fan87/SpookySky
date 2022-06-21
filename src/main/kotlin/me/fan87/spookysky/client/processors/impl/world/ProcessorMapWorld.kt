package me.fan87.spookysky.client.processors.impl.world

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.world.MapWorld
import me.fan87.spookysky.client.mapping.impl.world.MapWorldClient
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapWorld: Processor("Map World") {



    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenLdcStringEqual("MpServer")
        }
        for (method in clazz.node.methods) {
            if (pattern.matcher(method).next()) {
                MapWorldClient.map(clazz)
                MapWorld.map(MapWorldClient.getJavaClass().superclass)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapWorld.isMapped()
    }
}