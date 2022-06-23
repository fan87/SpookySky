package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.regbex.TypeExp
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.RenderEntityEvent
import me.fan87.spookysky.client.mapping.impl.entities.MapEntity
import me.fan87.spookysky.client.mapping.impl.rendering.MapRenderGlobal
import me.fan87.spookysky.client.mapping.impl.rendering.MapRenderManager
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.VarNumberManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.VarInsnNode
import java.io.File

class ProcessorMapRenderGlobal: Processor("Map RenderGlobal") {

    init {
        dependsOn(MapRenderManager.mapRenderEntitySimple)
        dependsOn(MapEntity)
    }

    val pattern = RegbexPattern {
        thenLdcStringEqual("entityOutlines")
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (pattern.matcher(method).next()) {
                MapRenderGlobal.map(clazz)
                MapRenderGlobal.mapRenderEntities.map(method)
                val hookPattern = RegbexPattern {
                    thenThis()
                    thenOpcodeCheck(Opcodes.GETFIELD)
                    thenGroup("entity2") {
                        thenOpcodeCheck(Opcodes.ALOAD)
                    }
                    thenOpcodeCheck(Opcodes.FLOAD)
                    thenCustomCheck {
                        it is MethodInsnNode && it.opcode == Opcodes.INVOKEVIRTUAL && it.name == MapRenderManager.mapRenderEntitySimple.assumeMapped().name && it.desc == MapRenderManager.mapRenderEntitySimple.assumeMapped().desc
                    }
                    thenGroup("end") {
                        thenOpcodeCheck(Opcodes.POP)
                    }
                    thenLazyAmountOf(0..25) {
                        thenAny()
                    }
                    thenOpcodeCheck(Opcodes.ACONST_NULL)
                    thenCustomCheck { it.opcode == Opcodes.PUTFIELD && it is FieldInsnNode && it.desc == MapEntity.assumeMapped().getDescName() }

                    thenLazyAmountOf(0..15) {
                        thenAny()
                    }
                    thenGroup("break") {
                        thenOpcodeCheck(Opcodes.GOTO)
                    }
                }
                val matcher = hookPattern.matcher(method)
                val varManager = VarNumberManager(method)
                if (matcher.next()) {
                    val out = InsnList()
                    val entityVar = (matcher.group("entity2")!![0] as VarInsnNode).`var`
                    val exitPoint = LabelNode()
                    for (withIndex in method.instructions.withIndex()) {
                        val index = withIndex.index
                        val instruction = withIndex.value
                        if (index == matcher.startIndex()) {
                            out.add(ASMUtils.generateNewEventPostAndPushToStack<RenderEntityEvent>(InsnList().also {
                                it.add(VarInsnNode(Opcodes.ALOAD, entityVar))
                            }, varManager))
                            out.addGetField(RenderEntityEvent::cancelled)
                            out.add(JumpInsnNode(Opcodes.IFNE, exitPoint))
                        }
                        if (index == matcher.groupEnd("end")) {
                            out.add(exitPoint)
                        }
                        out.add(instruction)
                    }
                }
                File("/tmp/RenderGlobal.class").writeBytes(ASMUtils.writeClass(clazz.node))
                return true
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapRenderGlobal.isMapped()
    }
}