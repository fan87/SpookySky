package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.MapEntityRenderer
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.Opcodes

class ProcessorMapEntityRendererMembers: Processor("Map EntityRenderer Members") {

    init {
        dependsOn(MapEntityRenderer)
    }

    override fun start() {
        onlyProcess(MapEntityRenderer)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val orientCameraPattern = RegbexPattern {
            thenOpcodeCheck(Opcodes.FCONST_0)
            thenLdc(0.3F)
            thenOpcodeCheck(Opcodes.FCONST_0)
        }
        for (method in clazz.node.methods) {
            if (orientCameraPattern.matcher(method).next()) {
                MapEntityRenderer.mapOrientCamera.map(method)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapEntityRenderer.mapOrientCamera.isMapped()
    }
}