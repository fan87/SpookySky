package me.fan87.spookysky.client.processors.impl.entities

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityLivingBase
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapMoveStrafing: Processor("Map EntityLivingBase.moveStrafing/moveForward") {

    init {
        dependsOn(MapEntityLivingBase)
    }

    override fun start() {
        onlyProcess(MapEntityLivingBase)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenLdc(0.98f)
            thenOpcodeCheck(Opcodes.IMUL)
            thenGroup("moveStrafing") {
                thenOpcodeCheck(Opcodes.PUTFIELD)
            }
            thenLazyAmountOf(0..8) {
                thenAny()
            }
            thenLdc(0.98f)
            thenOpcodeCheck(Opcodes.IMUL)
            thenGroup("moveForward") {
                thenOpcodeCheck(Opcodes.PUTFIELD)
            }
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapEntityLivingBase.mapOnLivingUpdate.map(method)
                MapEntityLivingBase.mapMoveStrafing.map(matcher.groupAsFieldInsnNode("moveStrafing"))
                MapEntityLivingBase.mapMoveStrafing.map(matcher.groupAsFieldInsnNode("moveForward"))
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapEntityLivingBase.mapMoveStrafing.isMapped()
    }
}