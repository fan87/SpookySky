package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.PostRender3DEvent
import me.fan87.spookysky.client.mapping.impl.rendering.MapEntityRenderer
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.VarInsnNode
import java.io.File

class ProcessorHookRenderPost3D: Processor("Hook Post Render 3D Events") {

    init {
        Thread.sleep(1000)
    }

    val hand = RegbexPattern {
        thenLdcStringEqual("translucent")
        thenLazyAnyAmountOf {
            thenAny()
        }
        thenLdcStringEqual("hand")
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = hand.matcher(method)
            if (matcher.next()) {
                val out = InsnList()
                MapEntityRenderer.map(clazz)
                MapEntityRenderer.mapRenderWorldPass.map(method)
                for (instruction in method.instructions.withIndex()) {
                    if (instruction.index == matcher.endIndex()) {
                        out.add(ASMUtils.generateNewEventPost<PostRender3DEvent>(InsnList().also {
                            it.add(VarInsnNode(Opcodes.FLOAD, 2))
                        }))
                    }
                    out.add(instruction.value)
                }
                return true
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapEntityRenderer.mapRenderWorldPass.isMapped()
    }
}