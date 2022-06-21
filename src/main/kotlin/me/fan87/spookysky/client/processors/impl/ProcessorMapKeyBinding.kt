package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.settings.MapKeyBinding
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes
import java.lang.reflect.Modifier

class ProcessorMapKeyBinding: Processor("Map KeyBinding") {

    init {
        dependsOn(MapKeyBinding)
    }

    override fun start() {
        onlyProcess(MapKeyBinding)
    }


    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (method.name == "<init>") {
                val pattern = RegbexPattern {
                    thenThis()
                    thenVarLoadNode(1)
                    thenGroup("keyDescription") {
                        thenOpcodeCheck(Opcodes.PUTFIELD)
                    }
                    thenLazyAmountOf(1..5) {
                        thenAny()
                    }
                    thenThis()
                    thenVarLoadNode(2)
                    thenGroup("keyCode") {
                        thenOpcodeCheck(Opcodes.PUTFIELD)
                    }
                    thenLazyAmountOf(1..5) {
                        thenAny()
                    }
                    thenThis()
                    thenVarLoadNode(2)
                    thenGroup("keyCodeDefault") {
                        thenOpcodeCheck(Opcodes.PUTFIELD)
                    }
                    thenLazyAmountOf(1..5) {
                        thenAny()
                    }
                    thenThis()
                    thenVarLoadNode(3)
                    thenGroup("keyCategory") {
                        thenOpcodeCheck(Opcodes.PUTFIELD)
                    }
                }
                val matcher = pattern.matcher(method)
                if (!matcher.next()) {
                    unsupportedClient("Couldn't match constructor")
                }
                MapKeyBinding.mapKeyDescription.map(matcher.groupAsFieldInsnNode("keyDescription"))
                MapKeyBinding.mapKeyCode.map(matcher.groupAsFieldInsnNode("keyCode"))
                MapKeyBinding.mapKeyCodeDefault.map(matcher.groupAsFieldInsnNode("keyCodeDefault"))
                MapKeyBinding.mapKeyCategory.map(matcher.groupAsFieldInsnNode("keyCategory"))
            } else if (Modifier.isStatic(method.access)) {
                val pattern = RegbexPattern {
                    thenGroup("field") {
                        thenOpcodeCheck(Opcodes.PUTFIELD)
                    }
                }
                if (method.desc == "(IZ)V") {
                    val matcher = pattern.matcher(method)
                    MapKeyBinding.mapOnTick.map(method)
                    if (!matcher.next()) {
                        unsupportedClient("Couldn't find PUTFIELD in onTick")
                    }
                    MapKeyBinding.mapPressed.map(matcher.groupAsFieldInsnNode("field"))
                }
                if (method.desc == "(I)V") {
                    val matcher = pattern.matcher(method)
                    MapKeyBinding.mapSetKeyBindState.map(method)
                    if (!matcher.next()) {
                        unsupportedClient("Couldn't find PUTFIELD in setKeyBindState")
                    }
                    MapKeyBinding.mapPressTime.map(matcher.groupAsFieldInsnNode("field"))
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapKeyBinding.mapKeyDescription.isMapped()
    }
}