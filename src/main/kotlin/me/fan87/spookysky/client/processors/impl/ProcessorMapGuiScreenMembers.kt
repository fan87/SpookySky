package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.chat.MapIChatComponent
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiScreen
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.getMethod
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodInsnNode

class ProcessorMapGuiScreenMembers: Processor("Map GuiScreen") {

    init {
        dependsOn(MapGuiScreen)
        dependsOn(MapEntityPlayerSP)
    }

    override fun process(clazz: LoadedClass): Boolean {
        if (clazz.name == MapGuiScreen.mapped!!.name) {
            val sendChatMessage_SB = RegbexPattern {
                thenPushInt(0)
                thenGroup("sendChatMessage_SB") {
                    thenCustomCheck { it.opcode == Opcodes.INVOKEVIRTUAL && it is MethodInsnNode && it.owner == clazz.node.name && it.desc == "(Ljava/lang/String;Z)V" }
                }
            }
            for (method in clazz.node.methods) {
                val matcher = sendChatMessage_SB.matcher(method)
                while (matcher.next()) {
                    val methodInsnNode = matcher.group("sendChatMessage_SB")!![0] as MethodInsnNode
                    val lol = clazz.node.getMethod(methodInsnNode)
                    val tester = RegbexPattern {
                        thenVarLoadNode(1)
                        thenGroup("sendChatMessage") {
                            thenCustomCheck {
                                it.opcode == Opcodes.INVOKEVIRTUAL && it is MethodInsnNode && it.owner == MapEntityPlayerSP.mapped!!.name
                            }
                        }
                        thenLazyAmountOf(0..4) {
                            thenAny()
                        }
                        thenReturn()
                    }
                    val match = tester.matcher(lol)
                    if (match.next()) {
                        MapEntityPlayerSP.mapSendChatMessage.map(match.group("sendChatMessage")!![0] as MethodInsnNode)
                        MapGuiScreen.mapSendChatMessage_SB.map(matcher.group("sendChatMessage_SB")!![0] as MethodInsnNode)
                        MapGuiScreen.mapHandleComponentClick.map(method)
                        MapIChatComponent.map(ASMUtils.descTypeToJvmType(ASMUtils.getParameterTypes(method.desc)[0]))
                        break
                    }
                }
            }
            assertMapped(MapGuiScreen.mapHandleComponentClick)
            assertMapped(MapGuiScreen.mapSendChatMessage_SB)
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapGuiScreen.mapSendChatMessage_SB.isMapped()
    }
}