package me.fan87.spookysky.client.processors.impl.entities

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.EntityJumpEvent
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import me.fan87.spookysky.client.mapping.impl.entities.MapEntity
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityLivingBase
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import me.fan87.spookysky.client.utils.VarNumberManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.VarInsnNode

class ProcessorMapEntityLivingJump: Processor("Map EntityLivingBase/.jump()") {
    val pattern = RegbexPattern {
        thenThis()
        thenGroup("isSprinting") {
            thenCustomCheck { it is MethodInsnNode && it.opcode == Opcodes.INVOKEVIRTUAL && it.desc == "()Z" }
        }
        thenOpcodeCheck(Opcodes.IFEQ)
        thenLazyAmountOf(0..4) {
            thenAny()
        }
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenOpcodeCheck(Opcodes.LDC)
        thenOpcodeCheck(Opcodes.FMUL)
        thenOpcodeCheck(Opcodes.FSTORE)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapEntityLivingBase.map(clazz)
                MapEntityLivingBase.mapJump.map(method)
                MapEntity.mapIsSprinting.map(matcher.groupAsMethodInsnNode("isSprinting"))
                val out = InsnList()
                val varNumberManager = VarNumberManager(method)
                val end = LabelNode()
                out.add(ASMUtils.generateNewEventPostAndPushToStack<EntityJumpEvent>(InsnList().apply {
                    add(VarInsnNode(Opcodes.ALOAD, 0))
                }, varNumberManager))
                out.addGetField(EntityJumpEvent::cancelled)
                out.add(JumpInsnNode(Opcodes.IFEQ, end))
                out.add(end)
                out.add(InsnNode(Opcodes.RETURN))
                out.add(method.instructions)
                method.instructions = out
                return true
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapEntityLivingBase.isMapped()
    }
}