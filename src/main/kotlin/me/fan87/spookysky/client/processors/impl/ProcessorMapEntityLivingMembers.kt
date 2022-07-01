package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityLiving
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityLivingBase
import me.fan87.spookysky.client.mapping.impl.item.MapItemStack
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapEntityLivingMembers: Processor("Map EntityLiving Members") {

    init {
        dependsOn(MapItemStack)
    }

    val pattern = RegbexPattern {
        thenLdcStringEqual("checkDespawn")
    }
    val patternB = RegbexPattern {
        thenThis()
        thenGroup("field") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenOpcodeCheck(Opcodes.ICONST_0)
        thenOpcodeCheck(Opcodes.AALOAD)
        thenOpcodeCheck(Opcodes.ARETURN)
    }


    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (pattern.matcher(method).next()) {
                MapEntityLiving.map(clazz)
            }
            for (method in clazz.node.methods) {
                val matcher = patternB.matcher(method)
                if (method.desc == "()${MapItemStack.assumeMapped().getDescName()}" && matcher.next()) {
                    MapEntityLiving.mapEquipment.map(matcher.groupAsFieldInsnNode("field"))
                    MapEntityLivingBase.mapGetHeldItem.map(method)
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapEntityLiving.isMapped()
    }
}