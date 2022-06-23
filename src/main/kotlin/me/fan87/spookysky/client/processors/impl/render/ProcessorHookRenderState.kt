package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.MapRender
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.render.RenderStateManager
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodNode
import kotlin.reflect.KMutableProperty

class ProcessorHookRenderState: Processor("Hook RenderState") {

    init {
        dependsOn(MapRender)
    }

    override fun start() {
        onlyProcess(MapRender)
    }

    override fun process(clazz: LoadedClass): Boolean {

        val renderShadowPattern = RegbexPattern {
            thenPushInt(770)
            thenPushInt(771)
            thenCustomCheck { it.opcode != Opcodes.ICONST_1 }
        }
        val renderLivingLabelPattern = RegbexPattern {
            thenPushInt(770)
            thenPushInt(771)
            thenCustomCheck { it.opcode == Opcodes.ICONST_1 }
        }
        for (method in clazz.node.methods) {
            if (renderShadowPattern.matcher(method).next()) {
                MapRender.mapRenderShadow.map(method)
                addHook(method, RenderStateManager::renderShadow)
            }
            if (renderLivingLabelPattern.matcher(method).next()) {
                MapRender.mapRenderLivingLabel.map(method)
                addHook(method, RenderStateManager::renderNameTag)
            }
        }
        assertMapped(MapRender.mapRenderShadow)
        assertMapped(MapRender.mapRenderLivingLabel)
        return true
    }

    companion object {
        fun addHook(method: MethodNode, field: KMutableProperty<Boolean>) {
            val out = InsnList()
            val end = LabelNode()
            out.addGetField(field)
            out.add(JumpInsnNode(Opcodes.IFNE, end))
            out.add(InsnNode(Opcodes.RETURN))
            out.add(end)
            for (instruction in method.instructions) {
                out.add(instruction)
            }
            method.instructions = out
        }
    }

    override fun jobDone(): Boolean {
        return MapRender.mapRenderLivingLabel.isMapped()
    }
}