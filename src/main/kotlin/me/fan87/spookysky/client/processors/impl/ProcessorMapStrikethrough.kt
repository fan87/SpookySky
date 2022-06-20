package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.Regbex
import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.MapChatStyle
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapStrikethrough: Processor("Map Strikethrough") {

    init {
        dependsOn(MapChatStyle)

        dependsOn(MapChatStyle.mapItalic)
        dependsOn(MapChatStyle.mapUnderlined)
        dependsOn(MapChatStyle.mapObfuscated)
        dependsOn(MapChatStyle.mapBold)
    }


    override fun start() {
        onlyProcess(MapChatStyle)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenOpcodeCheck(Opcodes.IFNONNULL)
            thenThis()
            thenGroup("field") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
        }

        for (method in clazz.node.methods) {
            if (method.desc == "()Z") {
                val matcher = pattern.matcher(method)
                outer@while (matcher.next()) {
                    val node = matcher.groupAsFieldInsnNode("field")
                    if (node.desc != "Ljava/lang/Boolean;") {
                        continue
                    }
                    for (child in MapChatStyle.children) {
                        if (child.mapped?.name == node.name) {
                            continue@outer
                        }
                    }
                    MapChatStyle.mapStrikethrough.map(node)
                    return false
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapChatStyle.mapStrikethrough.isMapped()
    }
}