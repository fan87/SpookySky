package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.ChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.IChatComponent
import me.fan87.spookysky.client.mapping.impl.chat.MapChatComponentText
import me.fan87.spookysky.client.mapping.impl.chat.MapIChatComponent
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.TypeInsnNode

class ProcessorMapComponents: Processor("Map Components") {

    val chatComponentText = RegbexPattern {
        thenGroup("ChatComponentText") {
            thenOpcodeCheck(Opcodes.NEW)
        }
        thenOpcodeCheck(Opcodes.DUP)
        thenLdcStringMatches(Regex("Outdated client! Please use .*"))
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = chatComponentText.matcher(method)
            if (matcher.next()) {
                MapChatComponentText.map(matcher.group("ChatComponentText")!![0] as TypeInsnNode)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapChatComponentText.isMapped()
    }
}