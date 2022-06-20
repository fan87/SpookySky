package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.EnumChatFormatting
import me.fan87.spookysky.client.mapping.impl.chat.MapChatStyle
import me.fan87.spookysky.client.mapping.impl.chat.MapEnumChatFormatting
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.getMethod
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes
import java.lang.reflect.Modifier

class ProcessorMapEnumChatFormatting: Processor("Map EnumChatFormatting") {

    init {
    }




    override fun process(clazz: LoadedClass): Boolean {

        val pattern = RegbexPattern {
            thenLdcStringEqual("DARK_GREEN")
        }
        val constructorPattern = RegbexPattern {
            thenVarLoadNode(3)
            thenGroup("name") {
                thenOpcodeCheck(Opcodes.PUTFIELD)
            }
        }
        if (clazz.node.access and Opcodes.ACC_ENUM != 0) {
            try {
                if (pattern.matcher(clazz.node.getMethod("<clinit>", "()V")).next()) {
                    MapEnumChatFormatting.map(clazz)
                    val method = clazz.node.getMethod("<init>", "(Ljava/lang/String;ILjava/lang/String;CZI)V")
                    val matcher = constructorPattern.matcher(method)
                    if (matcher.next()) {
                        MapEnumChatFormatting.mapName.map(matcher.groupAsFieldInsnNode("name"))
                    }

                    for (method in clazz.node.methods) {
                        if (method.desc == "(Ljava/lang/String;)L${clazz.node.name};") {
                            MapEnumChatFormatting.mapGetValueByName.map(method)
                        }
                    }
                }
            } catch (_: NoSuchElementException) {}
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapEnumChatFormatting.isMapped()
    }
}