package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexMatcher
import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.events.events.RenderEndFrameEvent
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.processors.HookingProcessor
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList

class ProcessorHookEndFrame: HookingProcessor("Hook EndFrame") {

    init {
//        dependsOn(MapMinecraft)
    }

    override fun start() {
//        onlyProcess(MapMinecraft)
    }

    override fun getPattern(): RegbexPattern {
        return RegbexPattern {
            thenLdcStringEqual("display_update")
        }
    }

    override fun getInsertIndex(matcher: RegbexMatcher): Int {
        return matcher.endIndex()
    }

    override fun insertOp(out: InsnList) {
        out.add(ASMUtils.generateNewEventPost<RenderEndFrameEvent>())
    }

}