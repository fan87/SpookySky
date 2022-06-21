package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.CrossHairEvent
import me.fan87.spookysky.client.mapping.impl.rendering.GuiIngame
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiIngame
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.ASMUtils.addMethodCall
import me.fan87.spookysky.client.utils.VarNumberManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.MethodInsnNode

class ProcessorHookCrossHair: Processor("Hook Crosshair") {

    init {
        dependsOn(MapGuiIngame)
    }

    override fun start() {
        onlyProcess(MapGuiIngame)
    }

    var done = false
    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val pattern = RegbexPattern {
                thenCustomCheck { it.opcode == Opcodes.INVOKEVIRTUAL && it is MethodInsnNode && it.owner == clazz.node.name && it.desc == "()Z" }
                thenGroup("ifeq") {
                    thenOpcodeCheck(Opcodes.IFEQ)
                }
                thenLazyAmountOf(0..25) {
                    thenAny()
                }
                thenPushInt(775)
                thenPushInt(769)
                thenPushInt(1)
                thenPushInt(0)
            }
            val varNumberManager = VarNumberManager(method)
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                val out = InsnList()
                for (instruction in method.instructions.withIndex()) {
                    if (instruction.index == matcher.startIndex() + 2) {
                        out.add(ASMUtils.generateNewEventPostAndPushToStack<CrossHairEvent>(varNumberManager = varNumberManager))
                        out.addGetField(CrossHairEvent::cancelled)
                        out.add(JumpInsnNode(Opcodes.IFNE, (matcher.group("ifeq")!![0] as JumpInsnNode).label))
                    }
                    out.add(instruction.value)
                }
                done = true
                return true
            }
        }
        unsupportedClient("Couldn't hook crosshair event")
    }

    override fun jobDone(): Boolean {
        return done
    }


}