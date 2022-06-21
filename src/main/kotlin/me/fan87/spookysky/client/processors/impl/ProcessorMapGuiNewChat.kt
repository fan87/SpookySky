package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.rendering.*
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.getJvmTypeName
import me.fan87.spookysky.client.utils.ASMUtils.getMethod
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import java.io.File

class ProcessorMapGuiNewChat: Processor("Map GuiNewChat") {

    init {
        dependsOn(MapMinecraft.mapIngameGui)
        dependsOn(MapGuiIngame)
        dependsOn(MapGuiScreen)
        dependsOn(MapGuiScreen.mapSendChatMessage)
    }

    override fun start() {
        onlyProcess(MapGuiScreen.assumeMapped().name)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val method = clazz.node.getMethod(MapGuiScreen.mapSendChatMessage)
        val pattern = RegbexPattern {
            thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.GETFIELD && it.owner == MapGuiScreen.assumeMapped().name && it.desc == MapMinecraft.assumeMapped().getDescName() }
            thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.GETFIELD && it.name == MapMinecraft.mapIngameGui.assumeMapped().name && it.desc == MapGuiIngame.assumeMapped().getDescName() }
            thenGroup("getChatGUI") {
                thenCustomCheck { it.opcode == Opcodes.INVOKEVIRTUAL && it is MethodInsnNode && it.owner == MapGuiIngame.assumeMapped().name }
            }
            thenVarLoadNode(1)
            thenGroup("addToSentMessages") {
                thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
            }
        }
        val matcher = pattern.matcher(method)
        matcher.next()
        MapGuiIngame.mapGetChatGUI.map(matcher.group("getChatGUI")!![0] as MethodInsnNode)
        MapGuiNewChat.map((matcher.group("addToSentMessages")!![0] as MethodInsnNode).owner)
        MapGui.map(MapGuiNewChat.getJavaClass().superclass.getJvmTypeName())
        MapGuiNewChat.mapAddToSentMessages.map(matcher.group("addToSentMessages")!![0] as MethodInsnNode)
        return false
    }

    override fun jobDone(): Boolean {
        return MapGuiNewChat.isMapped()
    }


}