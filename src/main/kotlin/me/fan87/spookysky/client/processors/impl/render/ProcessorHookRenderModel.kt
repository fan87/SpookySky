package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.PostRenderEntityModelEvent
import me.fan87.spookysky.client.events.events.RenderEntityModelEvent
import me.fan87.spookysky.client.mapping.impl.rendering.MapModelBase
import me.fan87.spookysky.client.mapping.impl.rendering.MapRenderLivingEntity
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import me.fan87.spookysky.client.utils.VarNumberManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.VarInsnNode

class ProcessorHookRenderModel: Processor("Hook renderModel") {

    init {
        dependsOn(MapRenderLivingEntity)
    }

    override fun start() {
        onlyProcess(MapRenderLivingEntity)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenLdc(0.003921569f)
            thenLazyAmountOf(0..200) {
                thenAny()
            }
            thenGroup("pre") {
                thenAny()
            }
            thenThis()
            thenGroup("field") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
            for (i in 1..7) {
                thenVarLoadNode(i)
            }

            thenGroup("method") {
                thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
            }
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapModelBase.map(matcher.groupAsMethodInsnNode("method").owner)
                MapModelBase.mapRender.map(matcher.groupAsMethodInsnNode("method"))
                MapRenderLivingEntity.mapMainModel.map(matcher.groupAsFieldInsnNode("field"))
                val out = InsnList()
                val endPost = LabelNode()
                val varNumberManager = VarNumberManager(method)
                for (withIndex in method.instructions.withIndex()) {
                    if (withIndex.index == matcher.groupEnd("pre")) {
                        out.add(ASMUtils.generateNewEventPostAndPushToStack<RenderEntityModelEvent>(InsnList().also {
                            it.add(VarInsnNode(Opcodes.ALOAD, 0))
                            it.add(VarInsnNode(Opcodes.ALOAD, 1))
                            for (i in 2..7) {
                                it.add(VarInsnNode(Opcodes.FLOAD, i))
                            }
                        }, varNumberManager))
                        out.addGetField(RenderEntityModelEvent::cancelled)
                        out.add(JumpInsnNode(Opcodes.IFNE, endPost))
                    }
                    if (withIndex.index == matcher.endIndex()) {
                        out.add(ASMUtils.generateNewEventPost<PostRenderEntityModelEvent>(InsnList().also {
                            it.add(VarInsnNode(Opcodes.ALOAD, 0))
                            it.add(VarInsnNode(Opcodes.ALOAD, 1))
                            for (i in 2..7) {
                                it.add(VarInsnNode(Opcodes.FLOAD, i))
                            }
                        }))
                        out.add(endPost)
                    }
                    out.add(withIndex.value)
                }
                return true
            }

        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapModelBase.isMapped()
    }
}