package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.MapChatComponentTranslation
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiNewChat
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsTypeInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapPrintChatMessage: Processor("Map printCheatMessage()") {
    val pattern = RegbexPattern {
        thenLdcStringEqual("demo.help.jump")
        thenAnyAmountOf {
            thenAny()
        }
        thenOpcodeCheck(Opcodes.INVOKESPECIAL)
        thenGroup("printChatMessage") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
    }

    override fun process(clazz: LoadedClass): Boolean {


        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                val newPattern = RegbexPattern {
                    thenGroup("ChatComponentTranslation") {
                        thenOpcodeCheck(Opcodes.NEW)
                    }
                    thenOpcodeCheck(Opcodes.DUP)
                    thenLdcStringEqual("demo.help.jump")
                    thenAnyAmountOf {
                        thenAny()
                    }
                    thenOpcodeCheck(Opcodes.INVOKESPECIAL)
                    thenGroup("printChatMessage") {
                        thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
                    }
                }
                val matcher1 = newPattern.matcher(method)
                if (matcher1.next()) {
                    MapChatComponentTranslation.map(matcher1.groupAsTypeInsnNode("ChatComponentTranslation"))
                    MapGuiNewChat.mapPrintChatMessage.map(matcher1.groupAsMethodInsnNode("printChatMessage"))
                }

            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapGuiNewChat.mapPrintChatMessage.isMapped()
    }
}