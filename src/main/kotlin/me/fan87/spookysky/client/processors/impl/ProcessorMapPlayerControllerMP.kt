package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.world.MapWorldClient
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapPlayerControllerMP: Processor("Map PlayerControllerMP") {

    init {
        dependsOn(MapWorldClient)
    }

    override fun start() {
        onlyProcess(MapWorldClient)
    }

    override fun process(clazz: LoadedClass): Boolean {
//        for (method in clazz.node.methods) {
//            if (method.name == "<init>") {
//                val pattern = RegbexPattern {
//                    thenThis()
//                }
//            }
//        }
        return false
    }

    override fun jobDone(): Boolean {
        return true
//        return MapPlayerControllerMP.isMapped()
    }
}