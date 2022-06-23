package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.impl.entities.MapEntity
import me.fan87.spookysky.client.mapping.impl.rendering.MapRender
import me.fan87.spookysky.client.mapping.impl.rendering.MapRenderLivingEntity
import me.fan87.spookysky.client.mapping.impl.rendering.Render
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.render.RenderStateManager
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import java.io.File

class ProcessorHookRenderLivingEntity: Processor("Hook RenderLivingEntity") {

    init {
        dependsOn(MapRender)
        dependsOn(MapEntity)
    }

    val pattern = RegbexPattern {
        thenLdc(-0.02666667F)
        thenLazyAmountOf(0..30) {
            thenAny()
        }
        thenLdc(-0.02666667F)
        thenLazyAmountOf(0..30) {
            thenAny()
        }
        thenLdc(9.374999F)
    }
    val afterMatchPattern = RegbexPattern {
        thenThis()
        thenVarLoadNode(1)
        thenGroup("method") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
        thenGroup("label") {
            thenOpcodeCheck(Opcodes.IFEQ)
        }
    }

    var processed = false
    override fun process(clazz: LoadedClass): Boolean {
        if (clazz.node.superName != MapRender.assumeMapped().name) return false
        for (renderName in clazz.node.methods) {

            if (pattern.matcher(renderName).next()) {
                MapRenderLivingEntity.map(clazz)
                val matcher = afterMatchPattern.matcher(renderName)
                if (matcher.next()) {
                    val node = matcher.groupAsMethodInsnNode("method")
                    val canRenderName = clazz.node.methods.first { node.name == it.name && node.desc == it.desc }
                    run {
                        val out = InsnList()
                        val label = (matcher.group("label")!![0] as JumpInsnNode).label
                        for (withIndex in renderName.instructions.withIndex()) {
                            val index = withIndex.index
                            val instruction = withIndex.value
                            if (index == matcher.groupEnd("label")) {
                                out.addGetField(RenderStateManager::renderNameTag)
                                out.add(JumpInsnNode(Opcodes.IFEQ, label))
                            }
                            out.add(instruction)
                        }
                        renderName.instructions = out
                    }
                    val out = InsnList()
                    val end = LabelNode()
                    out.addGetField(RenderStateManager::renderNameTag)
                    out.add(JumpInsnNode(Opcodes.IFNE, end))
                    out.add(InsnNode(Opcodes.ICONST_0))
                    out.add(InsnNode(Opcodes.IRETURN))
                    MapRender.mapCanRenderName.map(canRenderName)
                    out.add(end)
                    for (instruction in canRenderName.instructions) {
                        out.add(instruction)
                    }
                    canRenderName.instructions = out
                    processed = true
                    return true
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return processed
    }


}