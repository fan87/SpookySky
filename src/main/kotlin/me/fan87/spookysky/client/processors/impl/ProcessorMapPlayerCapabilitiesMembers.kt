package me.fan87.spookysky.client.processors.impl

import jdk.internal.org.objectweb.asm.Opcodes
import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.entities.AbilityFieldMapping
import me.fan87.spookysky.client.mapping.impl.entities.MapPlayerCapabilities
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.tree.JumpInsnNode

class ProcessorMapPlayerCapabilitiesMembers: Processor("Map PlayerCapabilities Members") {

    init {
        // We only want to process PlayerCapabilities
        dependsOn(MapPlayerCapabilities)
    }

    override fun start() {
        onlyProcess(MapPlayerCapabilities)
    }

    override fun process(clazz: LoadedClass): Boolean {
        // We want to loop through every method and see if it's the write one, not the read one


        val negativeFilter = RegbexPattern {
            thenCustomCheck { it is JumpInsnNode }
        }
        val abilities = MapPlayerCapabilities.children.filterIsInstance<AbilityFieldMapping<*>>()
        val positiveFilter = RegbexPattern {
            for (field in abilities) {
                thenLazyAnyAmountOf {
                    thenAny()
                }
                thenOpcodeCheck(Opcodes.ALOAD)
                thenLdcStringEqual(field.nbtName)
                thenThis()
                thenGroup(field.humanReadableName) {
                    thenOpcodeCheck(Opcodes.GETFIELD)
                }
                thenLazyAnyAmountOf {
                    thenAny()
                }
            }
        }

        for (method in clazz.node.methods) {
            if (negativeFilter.matcher(method).next()) continue // If it has any JumpInsnNode, it will skip it
            val matcher = positiveFilter.matcher(method)
            if (!matcher.next()) continue // If it doesn't match, skip it
            MapPlayerCapabilities.mapWriteCapabilitiesToNBT.map(method)
            for (ability in abilities) {
                ability.map(matcher.groupAsFieldInsnNode(ability.humanReadableName))
            }
        }

        assertMapped(MapPlayerCapabilities.mapWriteCapabilitiesToNBT) // If nothing matched, it will kill the process
        return false

    }

    override fun jobDone(): Boolean {
        return MapPlayerCapabilities.mapWriteCapabilitiesToNBT.isMapped()
    }
}