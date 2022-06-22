package me.fan87.spookysky.client.processors.impl

import org.objectweb.asm.Opcodes
import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.KeyEvent
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.ASMUtils.getMethod
import me.fan87.spookysky.client.utils.VarNumberManager
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.VarInsnNode
import java.io.File

class ProcessorHookKeyEvent: Processor("Hook KeyEvent") {

    init {
        dependsOn(MapMinecraft)
        dependsOn(MapMinecraft.mapRunTick)
        dependsOn(MapMinecraft.mapEntityRenderer)
    }

    override fun start() {
        onlyProcess(MapMinecraft)
    }

    override fun process(clazz: LoadedClass): Boolean {
        File("C:\\\\Users\\moon0\\Desktop\\Minecraft.class").writeBytes(ASMUtils.writeClass(clazz.node))
        val pattern = RegbexPattern {
            thenGroup("keyNumber") {
                thenOpcodeCheck(Opcodes.ILOAD)
            }
            thenPushInt(62)
            thenOpcodeCheck(Opcodes.IF_ICMPNE)
            thenLazyAmountOf(0..4) {
                thenAny()
            }
            thenThis()
            thenCustomCheck {
                it is FieldInsnNode && it.owner == MapMinecraft.assumeMapped().name && it.name == MapMinecraft.mapEntityRenderer.assumeMapped().name
            }
            thenOpcodeCheck(Opcodes.IFNULL)
        }
        val runTickMethod = clazz.node.getMethod(MapMinecraft.mapRunTick)
        val matcher = pattern.matcher(runTickMethod)
        assertTrue(matcher.next())
        val keyVarNumber = (matcher.group("keyNumber")!![0] as VarInsnNode).`var`
        val out = InsnList()
        val groupStart = matcher.groupStart("keyNumber")
        for (withIndex in runTickMethod.instructions.withIndex()) {
            val index = withIndex.index
            val insn = withIndex.value
            if (index == groupStart) {
                out.add(ASMUtils.generateNewEventPost<KeyEvent>(InsnList().also {
                    it.add(VarInsnNode(Opcodes.ILOAD, keyVarNumber))
                }))
            }
            out.add(insn)
        }
        runTickMethod.instructions = out
        processDone = true
        return true
    }
    var processDone = false

    override fun jobDone(): Boolean {
        return processDone
    }
}