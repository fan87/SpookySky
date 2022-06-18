package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.MappedClassInfo
import me.fan87.spookysky.client.mapping.MappedMethodInfo
import me.fan87.spookysky.client.mapping.impl.MapGuiScreen
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.TypeInsnNode
import java.lang.reflect.Modifier

class ProcessorMapMinecraft: Processor("Map Minecraft") {

    val minecraftPattern = RegbexPattern {
        thenLdcStringEqual("Manually triggered debug crash")
    }
    val patternA = RegbexPattern {
        thenGroup("GuiScreen") {
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
    }
    override fun process(clazz: LoadedClass): Boolean {

        for (method in clazz.node.methods) {
            if (minecraftPattern.matcher(method).next()) {
                MapMinecraft.mapped = MappedClassInfo(clazz.node.name)
                for (method in clazz.node.methods) {
                    if (Modifier.isStatic(method.access) && method.desc == "()L" + clazz.node.name + ";") {
                        MapMinecraft.mapGetMinecraft.mapped = MappedMethodInfo(method.name, method.desc)
                        println(Minecraft.getMinecraft().original)
                    }

                    run patternATest@{
                        val matcher = patternA.matcher(method)
                        if (matcher.next()) {
                            MapGuiScreen.map((matcher.group("GuiScreen")!![0] as TypeInsnNode).desc)
                            MapMinecraft.mapDisplayGuiScreen.map(matcher.group("displayGuiScreen")!![0] as MethodInsnNode)
                            MapMinecraft.mapLeftClickCounter.map(matcher.group("leftClickCounter")!![0] as FieldInsnNode)
                            MapMinecraft.mapSetIngameFocus.map(method)
                        }
                    }
                }
                assertMapped(MapMinecraft.mapLeftClickCounter)
                assertMapped(MapMinecraft.mapSetIngameFocus)
                assertMapped(MapMinecraft.mapDisplayGuiScreen)
                assertMapped(MapGuiScreen)
            }
        }


        return false
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.isMapped()
    }
}