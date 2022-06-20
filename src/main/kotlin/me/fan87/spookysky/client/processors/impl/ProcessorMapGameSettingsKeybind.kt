package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.settings.MapGameSettings
import me.fan87.spookysky.client.mapping.impl.settings.KeyBindMapping
import me.fan87.spookysky.client.mapping.impl.settings.MapKeyBinding
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsTypeInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapGameSettingsKeybind: Processor("Map GameSettings KeyBindings") {
    override fun process(clazz: LoadedClass): Boolean {
        methodLoop@for (method in clazz.node.methods) {
            for (child in MapGameSettings.children) {
                if (child is KeyBindMapping) {
                    val pattern = RegbexPattern {
                        thenLdcStringEqual(child.description)
                    }
                    if (!pattern.matcher(method).next()) {
                        continue@methodLoop
                    }
                }
            }
            for (child in MapGameSettings.children) {
                if (child is KeyBindMapping) {
                    val pattern = RegbexPattern {
                        thenThis()
                        thenGroup("KeyBinding") {
                            thenOpcodeCheck(Opcodes.NEW)
                        }
                        thenOpcodeCheck(Opcodes.DUP)
                        thenLdcStringEqual(child.description)
                        thenAny()
                        thenAny()
                        thenAny()
                        thenGroup("field") {
                            thenOpcodeCheck(Opcodes.PUTFIELD)
                        }
                    }
                    val matcher = pattern.matcher(method)
                    if (!matcher.next()) {
                        throw IllegalStateException("Matched method, but could not match all KeyBindings (${child.description})")
                    }
                    MapKeyBinding.map(matcher.groupAsTypeInsnNode("KeyBinding"))
                    child.map(matcher.groupAsFieldInsnNode("field"))
                    MapGameSettings.map(clazz)
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapGameSettings.mapKeyBindAttack.isMapped()
    }
}