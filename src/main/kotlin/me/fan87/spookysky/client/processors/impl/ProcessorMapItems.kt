package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.item.ItemsMapping
import me.fan87.spookysky.client.mapping.impl.item.MapItem
import me.fan87.spookysky.client.mapping.impl.item.MapItems
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsLdcStringCst
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.LdcInsnNode

class ProcessorMapItems: Processor("Map Items") {
    val pattern = RegbexPattern {
        thenLdcStringEqual("Accessed Items before Bootstrap!")
    }
    val patternB = RegbexPattern {
        thenGroup("name") {
            thenCustomCheck{it.opcode == Opcodes.LDC && it is LdcInsnNode && it.cst is String}
        }
        thenGroup("method") {
            thenOpcodeCheck(Opcodes.INVOKESTATIC)
        }
        thenOptional {
            thenOpcodeCheck(Opcodes.CHECKCAST)
        }
        thenGroup("field") {
            thenOpcodeCheck(Opcodes.PUTSTATIC)
        }
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (method.name == "<clinit>" && pattern.matcher(method).next()) {
                MapItems.map(clazz)
                val matcher = patternB.matcher(method)
                while (matcher.next()) {
                    if (!MapItem.isMapped()) {
                        MapItem.map(ASMUtils.descTypeToJvmType(ASMUtils.getReturnType(matcher.groupAsMethodInsnNode("method").desc)))
                    }
                    for (child in MapItems.children) {
                        if (child is ItemsMapping && child.humanReadableName == matcher.groupAsLdcStringCst("name")) {
                            child.map(matcher.groupAsFieldInsnNode("field"))
                        }
                    }
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapItems.isMapped()
    }
}