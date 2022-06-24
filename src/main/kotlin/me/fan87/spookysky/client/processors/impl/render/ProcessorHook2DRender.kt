package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.Render2DPreBossBarEvent
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiIngame
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.insertInstructions
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList

class ProcessorHook2DRender: Processor("Hook 2DRender") {

    init {
        dependsOn(MapGuiIngame)
    }

    override fun start() {
        onlyProcess(MapGuiIngame)
    }

    val pattern = RegbexPattern {
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenLdcStringEqual("bossHealth")
    }
    var processed = false

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                method.instructions = method.instructions.insertInstructions(matcher.startIndex()) {
                    it.add(ASMUtils.generateNewEventPost<Render2DPreBossBarEvent>())
                }
                processed = true
                return true
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return processed
    }
}