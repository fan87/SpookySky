package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.MapChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.MapChatStyle
import me.fan87.spookysky.client.mapping.impl.chat.MapIChatComponent
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.TypeInsnNode

class ProcessorMapSetChatStyle: Processor("Map setChatStyle") {

    init {
        dependsOn(MapChatComponentText)
    }

    val pattern = RegbexPattern {
        thenOpcodeCheck(Opcodes.NEW)
        thenOpcodeCheck(Opcodes.DUP)
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenOpcodeCheck(Opcodes.INVOKESPECIAL)
        thenLazyAmountOf(0..7) {
            thenAny()
        }
        thenGroup("getChatStyle") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
        thenGroup("createShallowCopy") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
        thenGroup("setChatStyle") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
    }

    override fun process(clazz: LoadedClass): Boolean {
        if (clazz.node.name != MapChatComponentText.assumeMapped().name) return false
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (method.desc.endsWith(")L${MapChatComponentText.assumeMapped().name};") && matcher.next()) {
                MapIChatComponent.mapGetChatStyle.map(matcher.groupAsMethodInsnNode("getChatStyle"))
                MapChatStyle.map(matcher.groupAsMethodInsnNode("createShallowCopy").owner)
                MapChatStyle.mapCreateShallowCopy.map(matcher.groupAsMethodInsnNode("createShallowCopy"))
                MapIChatComponent.mapSetChatStyle.map(matcher.groupAsMethodInsnNode("setChatStyle"))
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapIChatComponent.mapSetChatStyle.isMapped()
    }
}