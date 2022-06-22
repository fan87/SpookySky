package me.fan87.spookysky.client.processors.impl.entities

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.entities.MapEntity
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapEntityId: Processor("Map entityId") {

    init {
        dependsOn(MapEntity)
    }

    override fun start() {
        onlyProcess(MapEntity)
    }

    val pattern = RegbexPattern {
        thenLdcStringEqual("Entity ID")
        thenThis()
        thenGroup("entityId") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
    }
    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapEntity.mapEntityId.map(matcher.groupAsFieldInsnNode("entityId"))
                return false
            }
        }
        assertMapped(MapEntity.mapEntityId)
        return false
    }

    override fun jobDone(): Boolean {
        return MapEntity.mapEntityId.isMapped()
    }
}