package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.MapChatStyle
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapChatStyle: Processor("Map ChatStyle") {

    init {
        dependsOn(MapChatStyle)
    }

    override fun start() {
        onlyProcess(MapChatStyle.assumeMapped().name)
    }

    var processed = false

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenLdcStringMatches(Regex(".*hasParent.*"))
            thenLazyAnyAmountOf { thenAny() }
            thenGroup("parentStyle") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
            thenLazyAnyAmountOf { thenAny() }
            thenLdcStringMatches(Regex(".*color.*"))

            thenLazyAnyAmountOf { thenAny() }
            thenGroup("color") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
            thenLazyAnyAmountOf { thenAny() }
            thenLdcStringMatches(Regex(".*bold.*"))

            thenLazyAnyAmountOf { thenAny() }
            thenGroup("bold") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
            thenLazyAnyAmountOf { thenAny() }
            thenLdcStringMatches(Regex(".*italic.*"))

            thenLazyAnyAmountOf { thenAny() }
            thenGroup("italic") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
            thenLazyAnyAmountOf { thenAny() }
            thenLdcStringMatches(Regex(".*underlined.*"))

            thenLazyAnyAmountOf { thenAny() }
            thenGroup("underlined") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
            thenLazyAnyAmountOf { thenAny() }
            thenLdcStringMatches(Regex(".*obfuscated.*"))
            thenLazyAnyAmountOf { thenAny() }
            thenGroup("obfuscated") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
        }
        for (method in clazz.node.methods) {
            if (method.name == "toString") {
                val matcher = pattern.matcher(method)
                matcher.next()
                MapChatStyle.mapParentStyle.map(matcher.groupAsFieldInsnNode("parentStyle"))
                MapChatStyle.mapColor.map(matcher.groupAsFieldInsnNode("color"))
                MapChatStyle.mapBold.map(matcher.groupAsFieldInsnNode("bold"))
                MapChatStyle.mapItalic.map(matcher.groupAsFieldInsnNode("italic"))
                MapChatStyle.mapUnderlined.map(matcher.groupAsFieldInsnNode("underlined"))
                MapChatStyle.mapObfuscated.map(matcher.groupAsFieldInsnNode("obfuscated"))
                processed = true
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return processed
    }
}