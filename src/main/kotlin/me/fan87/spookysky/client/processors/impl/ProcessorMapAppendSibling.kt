package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.MapChatComponentStyle
import me.fan87.spookysky.client.mapping.impl.chat.MapIChatComponent
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapAppendSibling: Processor("Map appendSibling") {
    val pattern = RegbexPattern {
        thenLdc("options.difficulty")
        thenLazyAnyAmountOf {
            thenAny()
        }
        thenGroup("appendSibling") {
            thenOpcodeCheck(Opcodes.INVOKEINTERFACE)
        }
    }


    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapIChatComponent.mapAppendSibling.map(matcher.groupAsMethodInsnNode("appendSibling"))
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapIChatComponent.mapAppendSibling.isMapped()
    }
}