package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.MapChatComponentStyle
import me.fan87.spookysky.client.mapping.impl.chat.MapChatComponentText
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.getJvmTypeName
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.TypeInsnNode

class ProcessorMapComponentText: Processor("Map ChatComponentText") {

    val chatComponentText = RegbexPattern {
        thenLdcStringEqual("TextComponent{text='")
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = chatComponentText.matcher(method)
            if (matcher.next()) {
                MapChatComponentText.map(clazz)
                MapChatComponentStyle.map(MapChatComponentText.getJavaClass().superclass.getJvmTypeName())
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapChatComponentText.isMapped()
    }
}