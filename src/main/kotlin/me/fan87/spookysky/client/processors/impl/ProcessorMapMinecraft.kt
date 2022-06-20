package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.mapping.MappedClassInfo
import me.fan87.spookysky.client.mapping.MappedMethodInfo
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiChat
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiIngame
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiScreen
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsTypeInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import java.io.File
import java.lang.reflect.Modifier

class ProcessorMapMinecraft: Processor("Map Minecraft") {

    init {
        dependsOn(MapEntityPlayerSP)
        dependsOn(MapGuiIngame)
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
                mapIngameGui(clazz)
                matchPatternB(clazz)
                matchCurrentGuiScreen(clazz)
                matchFPS(clazz)
                matchGuiChat(clazz)
                try {
                    val file = File("/tmp/Minecraft.class")
                    file.createNewFile()
                    file.writeBytes(ASMUtils.writeClass(clazz.node))
                    println("File: ${file.absolutePath}")
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                return true
            }
        }


        return false
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
    fun mapIngameGui(clazz: LoadedClass) {
        for (field in clazz.node.fields) {
            if (field.desc == "L${MapGuiIngame.assumeMapped().name};") {
                MapMinecraft.mapIngameGui.map(field)
            }
        }
        assertMapped(MapMinecraft.mapIngameGui)
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
                } else {
                    MapMinecraft.mapRightClickMouse.map(method)
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