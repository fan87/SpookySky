package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.MapEntityRenderer
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapSetupCameraTransform: Processor("Map setupCameraTransform") {

    init {
        dependsOn(MapEntityRenderer)
    }

    override fun start() {
        onlyProcess(MapEntityRenderer)
    }

    val pattern = RegbexPattern {
        thenLdc(173.0F)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (pattern.matcher(method).next()) {
                MapEntityRenderer.mapSetupCameraTransform.map(method)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapEntityRenderer.mapSetupCameraTransform.isMapped()
    }
}