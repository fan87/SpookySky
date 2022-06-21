package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addMethodCall
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import me.fan87.spookysky.client.utils.ChatColor
import me.fan87.spookysky.client.utils.ColorUtils
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.IincInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.IntInsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.VarInsnNode
import java.io.File

class ProcessorRainbowChatColor: Processor("Rainbow Chat Color Hooker") {



    var hooked = false

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenGroup("colorChars") {
                thenLdcStringEqual("0123456789abcdefklmnor")
            }
            thenOpcodeCheck(Opcodes.ALOAD)
            thenLazyAmountOf(1..20) {
                thenAny()
            }
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL) // indexOf
            thenGroup("charIndex") {
                thenOpcodeCheck(Opcodes.ISTORE)
            }
            thenLazyAmountOf(0..10) {
                thenAny()
            }
            thenPushInt(16)
            thenOpcodeCheck(Opcodes.IF_ICMPGE)

            thenLazyAnyAmountOf {
                thenAny()
            }
            thenGroup("shadow") {
                thenOpcodeCheck(Opcodes.ILOAD)
            }
            thenOpcodeCheck(Opcodes.IFEQ)
            thenCustomCheck { it is LabelNode }
            thenLazyAmountOf(0..5) {
                thenAny()
            }
            thenCustomCheck {
                it.opcode == Opcodes.IINC && it is IincInsnNode && it.incr == 16
            }
            thenLazyAnyAmountOf {
                thenAny()
            }
            thenOpcodeCheck(Opcodes.ILOAD)
            thenPushInt(21)
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (!matcher.next()) {
                continue
            }
            println("Found renderStringAtPos: ${method.desc} ${clazz.node.name}")
            var shadow = (matcher.group("shadow")!![0] as VarInsnNode).`var`
            var charIndex = (matcher.group("charIndex")!![0] as VarInsnNode).`var`
            val strategyField = RegbexPattern {
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(-1)
                thenGroup("field") {
                    thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "I" }
                }
            }
            val strategyMethod = RegbexPattern {
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(0..4) {
                    thenAny()
                }
                thenThis()
                thenPushInt(0)
                thenCustomCheck { it is FieldInsnNode && it.opcode == Opcodes.PUTFIELD && it.desc == "Z" }
                thenLazyAmountOf(5..20) {
                    thenAny()
                }
                thenThis()
                thenGroup("alpha") {
                    thenOpcodeCheck(Opcodes.GETFIELD)
                }
                thenGroup("method") {
                    thenCustomCheck { it is MethodInsnNode && it.opcode == Opcodes.INVOKEVIRTUAL && it.desc == "(FFFF)V" }
                }
            }
            method.instructions = InsnList().also {
                val ldcInsnNode = matcher.group("colorChars")!![0] as LdcInsnNode
                for (abstractInsnNode in matcher.replaceGroup("colorChars", InsnList().apply {
                    add(LdcInsnNode(ldcInsnNode.cst.toString() + ChatColor.RAINBOW_COLOR_CHAR))
                })) {
                    it.add(abstractInsnNode)
                }
            }
            val out = InsnList()
            val end = LabelNode()
            val fieldMatcher = strategyField.matcher(method)
            val methodMatcher = strategyMethod.matcher(method)
            val useField = fieldMatcher.next()
            val useMethod = methodMatcher.next()
            if (useField && useMethod) {
                unsupportedClient("Matched field strategy and method strategy at the same time")
            }
            if (!useMethod && !useField) {
                unsupportedClient("Both of field strategy and method strategy don't work")
            }

            for (withIndex in method.instructions.withIndex()) {
                val instruction = withIndex.value
                val index = withIndex.index
                if (instruction.opcode == Opcodes.RETURN && index < method.instructions.size() / 4) {
                    continue
                }
                if (instruction.opcode == Opcodes.IFNULL) { // Support for Lunar Client
                    out.add(JumpInsnNode(Opcodes.GOTO, (instruction as JumpInsnNode).label))
                    continue
                }
                if (index == matcher.endIndex()) {
                    out.add(InsnNode(Opcodes.POP))
                    out.add(InsnNode(Opcodes.POP))
                    out.add(VarInsnNode(Opcodes.ILOAD, charIndex))
                    out.add(IntInsnNode(Opcodes.BIPUSH, 22))
                    out.add(JumpInsnNode(Opcodes.IF_ICMPNE, end))
                    out.add(VarInsnNode(Opcodes.ILOAD, shadow))
                    out.add(JumpInsnNode(Opcodes.IFNE, end))
                    // Inject Here
                    if (useField) {
                        out.add(VarInsnNode(Opcodes.ALOAD, 0))
                        out.addMethodCall(ColorUtils::getChatRainbowI)
                        val color = fieldMatcher.groupAsFieldInsnNode("field")
                        out.add(FieldInsnNode(Opcodes.PUTFIELD, color.owner, color.name, color.desc))
                    } else {
                        out.add(VarInsnNode(Opcodes.ALOAD, 0))
                        out.addMethodCall(ColorUtils::getChatRainbowR)
                        out.addMethodCall(ColorUtils::getChatRainbowG)
                        out.addMethodCall(ColorUtils::getChatRainbowB)
                        val alpha = methodMatcher.groupAsFieldInsnNode("alpha")
                        val setColor = methodMatcher.groupAsMethodInsnNode("method")
                        out.add(VarInsnNode(Opcodes.ALOAD, 0))
                        out.add(FieldInsnNode(Opcodes.GETFIELD, alpha.owner, alpha.name, alpha.desc))
                        out.add(MethodInsnNode(Opcodes.INVOKEVIRTUAL, setColor.owner, setColor.name, setColor.desc))
                    }
                    out.add(end)
                    out.add(VarInsnNode(Opcodes.ILOAD, charIndex))
                    out.add(IntInsnNode(Opcodes.BIPUSH, 21))
                }
                out.add(instruction)
            }
            method.instructions = out
            hooked = true
            return true
        }
        return false
    }

    override fun jobDone(): Boolean {
//        return hooked
        return true
    }


}