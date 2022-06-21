package me.fan87.spookysky.client.processors.impl.world

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.world.MapWorld
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapWorldMembers: Processor("Map theWorld") {

    init {
        dependsOn(MapWorld)
    }

    override fun start() {
        onlyProcess(MapWorld)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenLdcStringEqual("All: ")
            thenLazyAnyAmountOf {
                thenAny()
            }
            thenThis()
            thenGroup("field") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapWorld.mapLoadedEntityList.map(matcher.groupAsFieldInsnNode("field"))
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapWorld.mapLoadedEntityList.isMapped()
    }
}