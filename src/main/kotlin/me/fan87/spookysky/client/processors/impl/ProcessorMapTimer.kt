package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapTimer
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldInsnNode

class ProcessorMapTimer: Processor("Timer") {

    init {
        dependsOn(MapTimer)
    }

    override fun start() {
        onlyProcess(MapTimer)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val patternA = RegbexPattern {
            thenThis()
            thenGroup("timerSpeed") {
                thenCustomCheck { it.opcode == Opcodes.GETFIELD && it is FieldInsnNode && it.desc == "F" }
            }
            thenOpcodeCheck(Opcodes.F2D)
            thenOpcodeCheck(Opcodes.DMUL)
            thenThis()
            thenGroup("ticksPerSecond") {
                thenCustomCheck { it.opcode == Opcodes.GETFIELD && it is FieldInsnNode && it.desc == "F" }
            }
            thenOpcodeCheck(Opcodes.F2D)
            thenOpcodeCheck(Opcodes.DMUL)
            thenOpcodeCheck(Opcodes.DADD)
            thenOpcodeCheck(Opcodes.D2F)
            thenGroup("elapsedPartialTicks") {
                thenCustomCheck { it.opcode == Opcodes.PUTFIELD && it is FieldInsnNode && it.desc == "F" }
            }
        }
        for (method in clazz.node.methods) {
            val matcher = patternA.matcher(method)
            if (matcher.next()) {
                MapTimer.mapTimerSpeed.map(matcher.groupAsFieldInsnNode("timerSpeed"))
                MapTimer.mapTicksPerSecond.map(matcher.groupAsFieldInsnNode("ticksPerSecond"))
                MapTimer.mapElapsedPartialTicks.map(matcher.groupAsFieldInsnNode("elapsedPartialTicks"))

                val renderPartialTickPattern = RegbexPattern {
                    thenCustomCheck {
                        it.opcode == Opcodes.GETFIELD
                        && it is FieldInsnNode
                        && it.owner == MapTimer.assumeMapped().name
                        && it.name == MapTimer.mapElapsedPartialTicks.assumeMapped().name
                        && it.desc == "F" }
                    thenGroup("renderPartialTicks") {
                        thenCustomCheck { it.opcode == Opcodes.PUTFIELD && it is FieldInsnNode && it.desc == "F" && it.owner == MapTimer.assumeMapped().name }
                    }
                }
                val matcher1 = renderPartialTickPattern.matcher(method)
                matcher1.next()
                MapTimer.mapRenderPartialTicks.map(matcher1.groupAsFieldInsnNode("renderPartialTicks"))
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapTimer.mapTimerSpeed.isMapped()
    }
}