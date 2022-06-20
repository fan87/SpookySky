package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.AutoCompleteEvent
import me.fan87.spookysky.client.events.events.GuiChatMessageEvent
import me.fan87.spookysky.client.mapping.impl.rendering.MapGui
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiChat
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import me.fan87.spookysky.client.utils.VarNumberManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.io.File

class ProcessorMapGuiChat: Processor("Map GuiChat") {

    init {
        dependsOn(MapGuiChat)
        dependsOn(MapGui)
    }

    override fun start() {
        onlyProcess(MapGuiChat)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (method.desc == "([Ljava/lang/String;)V") {
                MapGuiChat.mapOnAutocompleteResponse.map(method)
            }
            if (method.desc == "(Ljava/lang/String;Ljava/lang/String;)V") {
                MapGuiChat.mapSendAutocompleteRequest.map(method)
                val pattern = RegbexPattern {
                    thenThis()
                    thenPushInt(1)
                    thenGroup("waitingOnAutocomplete") {
                        thenCustomCheck { it.opcode == Opcodes.PUTFIELD && it is FieldInsnNode && it.desc == "Z" }
                    }
                }
                val matcher = pattern.matcher(method)
                matcher.next()
                MapGuiChat.mapWaitingOnAutocomplete.map(matcher.groupAsFieldInsnNode("waitingOnAutocomplete"))
                val out = InsnList()
                val start = LabelNode()
                val end = LabelNode()
                val varNumberManager = VarNumberManager(method)
                out.add(start)
                out.add(ASMUtils.generateNewEventPostAndPushToStack<AutoCompleteEvent>(InsnList().also {
                    it.add(VarInsnNode(Opcodes.ALOAD, 1))
                }, varNumberManager))
                out.addGetField(AutoCompleteEvent::cancelled)
                out.add(JumpInsnNode(Opcodes.IFEQ, end))
                out.add(InsnNode(Opcodes.RETURN))
                out.add(end)
                out.add(method.instructions)
                method.instructions = out
            }
        }
        File("/tmp/test.class").writeBytes(ASMUtils.writeClass(clazz.node))
        assertMapped(MapGuiChat.mapOnAutocompleteResponse)
        assertMapped(MapGuiChat.mapSendAutocompleteRequest)
        return true
    }

    override fun jobDone(): Boolean {
        return MapGuiChat.mapOnAutocompleteResponse.isMapped()
    }


}