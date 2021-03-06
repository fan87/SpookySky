package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.ClickMouseEvent
import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.events.events.RightClickMouseEvent
import me.fan87.spookysky.client.mapping.MappedClassInfo
import me.fan87.spookysky.client.mapping.MappedMethodInfo
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.MapTimer
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.rendering.*
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsTypeInsnNode
import me.fan87.spookysky.client.utils.VarNumberManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import java.lang.reflect.Modifier

class ProcessorMapMinecraft: Processor("Map Minecraft") {

    init {
        dependsOn(MapEntityPlayerSP)
        dependsOn(MapEntityRenderer)
    }

    val minecraftPattern = RegbexPattern {
        thenLdcStringEqual("Manually triggered debug crash")
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (field in clazz.node.fields) {
            if (field.desc == "L${MapEntityPlayerSP.assumeMapped().name};") {
                MapMinecraft.mapThePlayer.map(field)
            }
        }
        for (method in clazz.node.methods) {
            if (minecraftPattern.matcher(method).next()) {
                MapMinecraft.mapped = MappedClassInfo(clazz.node.name)
                for (method in clazz.node.methods) {
                    matchGetMinecraft(clazz, method)
                }
                matchClickMouse(clazz)
                matchRunTick(clazz)
                matchPatternB(clazz)
                matchCurrentGuiScreen(clazz)
                matchFPS(clazz)
                matchGuiChat(clazz)
                matchEntityRenderer(clazz)
                matchTimer(clazz)
                matchFrameBuffer(clazz)
                matchRenderManager(clazz.node)
                return true
            }
        }
        return false
    }

    fun matchRenderManager(clazz: ClassNode) {
        for (field in clazz.fields) {
            if (field.desc == MapRenderManager.assumeMapped().getDescName()) {
                MapMinecraft.mapRenderManager.map(field)
            }
        }
    }

    fun matchFrameBuffer(clazz: LoadedClass) {
        val pattern = RegbexPattern {
            thenCustomCheck { it.opcode == Opcodes.INVOKESPECIAL && it is MethodInsnNode && it.desc == "(IIZ)V" }
            thenGroup("lol") {
                thenOpcodeCheck(Opcodes.PUTFIELD)
            }
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapFramebuffer.map(ASMUtils.descTypeToJvmType(matcher.groupAsFieldInsnNode("lol").desc))
                MapMinecraft.mapFramebufferMc.map(matcher.groupAsFieldInsnNode("lol"))
            }
        }
        assertMapped(MapFramebuffer)

    }

    fun matchTimer(clazz: LoadedClass) {
        val pattern = RegbexPattern {
            thenOpcodeCheck(Opcodes.DUP)
            thenLdc(20.0f)
            thenOpcodeCheck(Opcodes.INVOKESPECIAL)
            thenGroup("timer") {
                thenOpcodeCheck(Opcodes.PUTFIELD)
            }
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                val timer = matcher.groupAsFieldInsnNode("timer")
                MapMinecraft.mapTimer.map(timer)
                MapTimer.map(ASMUtils.descTypeToJvmType(timer.desc))
            }
        }
        assertMapped(MapTimer)
    }

    fun matchGuiChat(clazz: LoadedClass) {
        val pattern = RegbexPattern {
            thenGroup("GuiChat") {
                thenOpcodeCheck(Opcodes.NEW)
            }
            thenOpcodeCheck(Opcodes.DUP)
            thenLdc("/")
            thenOpcodeCheck(Opcodes.INVOKESPECIAL)
        }

        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapGuiChat.map(matcher.groupAsTypeInsnNode("GuiChat"))
            }
        }
        assertMapped(MapGuiChat)
    }
    fun matchEntityRenderer(clazz: LoadedClass) {
        for (field in clazz.node.fields) {
            if (field.desc == "L${MapEntityRenderer.assumeMapped().name};") {
                MapMinecraft.mapEntityRenderer.map(field)
            }
        }
        assertMapped(MapMinecraft.mapEntityRenderer)
    }

    fun matchRunTick(clazz: LoadedClass) {
        val pattern = RegbexPattern {
            thenLdcStringEqual("Updating screen events")
        }
        val runTick = clazz.node.methods.first { pattern.matcher(it).next() }
        MapMinecraft.mapRunTick.map(runTick)

        val out = InsnList()
        val start = LabelNode()
        out.add(start)
        out.add(ASMUtils.generateNewEventPost<ClientTickEvent>())
        out.add(runTick.instructions)
        runTick.instructions = out
        assertMapped(MapMinecraft.mapRunTick)
    }

    fun matchGetMinecraft(clazz: LoadedClass, method: MethodNode) {
        if (Modifier.isStatic(method.access) && method.desc == "()L" + clazz.node.name + ";") {
            MapMinecraft.mapGetMinecraft.mapped = MappedMethodInfo(method.name, method.desc)
        }
    }

    fun matchFPS(clazz: LoadedClass) {
        val pattern = RegbexPattern {
            thenLdcStringEqual("fps")
            thenLazyAnyAmountOf {
                thenAny()
            }
            thenGroup("debugFPS") {
                thenOpcodeCheck(Opcodes.GETSTATIC)
            }
            thenLazyAnyAmountOf {
                thenAny()
            }
            thenLdcStringEqual("vsync_enabled")
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapMinecraft.mapDebugFPS.map(matcher.group("debugFPS")!![0] as FieldInsnNode)
            }
        }
        assertMapped(MapMinecraft.mapDebugFPS)
    }

    fun matchClickMouse(clazz: LoadedClass) {
        val left = RegbexPattern {
            thenPushInt(10)
        }
        val both = RegbexPattern {
            thenLdcStringEqual("Null returned as 'hitResult', this shouldn't happen!")
        }
        for (method in clazz.node.methods) {
            if (both.matcher(method).next()) {
                if (left.matcher(method).next()) {
                    MapMinecraft.mapClickMouse.map(method)
                    val varManager = VarNumberManager(method)
                    val out = InsnList()
                    val end = LabelNode()
                    out.add(ASMUtils.generateNewEventPostAndPushToStack<ClickMouseEvent>(varNumberManager = varManager))
                    out.addGetField(ClickMouseEvent::cancelled)
                    out.add(JumpInsnNode(Opcodes.IFEQ, end))
                    out.add(InsnNode(Opcodes.RETURN))
                    out.add(end)
                    out.add(method.instructions)
                    method.instructions = out
                } else {
                    MapMinecraft.mapRightClickMouse.map(method)
                    val varManager = VarNumberManager(method)
                    val out = InsnList()
                    val end = LabelNode()
                    out.add(ASMUtils.generateNewEventPostAndPushToStack<RightClickMouseEvent>(varNumberManager = varManager))
                    out.addGetField(RightClickMouseEvent::cancelled)
                    out.add(JumpInsnNode(Opcodes.IFEQ, end))
                    out.add(InsnNode(Opcodes.RETURN))
                    out.add(end)
                    out.add(method.instructions)
                    method.instructions = out
                }
            }
        }
        assertMapped(MapMinecraft.mapClickMouse)
        assertMapped(MapMinecraft.mapRightClickMouse)
    }

    fun matchCurrentGuiScreen(clazz: LoadedClass) {
        for (field in clazz.node.fields) {
            if (field.desc == "L${MapGuiScreen.assumeMapped().name};") {
                MapMinecraft.mapCurrentScreen.map(field)
            }
        }
        assertMapped(MapMinecraft.mapCurrentScreen)
    }

    fun matchPatternB(clazz: LoadedClass) {
        val patternA = RegbexPattern {
            thenOpcodeCheck(Opcodes.ACONST_NULL)
            thenOptional {
                thenOpcodeCheck(Opcodes.CHECKCAST)
            }
            thenGroup("displayGuiScreen") {
                thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
            }
            thenAmountOf(0..4) {
                thenAny()
            }
            thenThis()
            thenPushInt(10000)
            thenGroup("leftClickCounter") {
                thenOpcodeCheck(Opcodes.PUTFIELD)
            }
            thenAmountOf(0..4) {
                thenAny()
            }
            thenReturn()
        }
        for (method in clazz.node.methods) {
            val matcher = patternA.matcher(method)
            if (matcher.next()) {
                MapGuiScreen.map(ASMUtils.descTypeToJvmType(ASMUtils.getParameterTypes((matcher.group("displayGuiScreen")!![0] as MethodInsnNode).desc)[0]))
                MapMinecraft.mapDisplayGuiScreen.map(matcher.group("displayGuiScreen")!![0] as MethodInsnNode)
                MapMinecraft.mapLeftClickCounter.map(matcher.group("leftClickCounter")!![0] as FieldInsnNode)
                MapMinecraft.mapSetIngameFocus.map(method)
            }
        }
        assertMapped(MapMinecraft.mapLeftClickCounter)
        assertMapped(MapMinecraft.mapSetIngameFocus)
        assertMapped(MapMinecraft.mapDisplayGuiScreen)
        assertMapped(MapGuiScreen)
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.isMapped()
    }
}