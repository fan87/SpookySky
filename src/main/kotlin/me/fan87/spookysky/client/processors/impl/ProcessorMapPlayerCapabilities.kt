package me.fan87.spookysky.client.processors.impl

import jdk.internal.org.objectweb.asm.Opcodes
import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayer
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.entities.MapPlayerCapabilities
import me.fan87.spookysky.client.mapping.impl.packets.play.client.MapC13PacketPlayerAbilities
import me.fan87.spookysky.client.mapping.impl.world.MapWorld
import me.fan87.spookysky.client.mapping.impl.world.MapWorldClient
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.TypeInsnNode
import java.io.File

class ProcessorMapPlayerCapabilities: Processor("Map PlayerCapabilities"){

    init {
        dependsOn(MapEntityPlayerSP)
        dependsOn(MapEntityPlayerSP.mapSendQueue)
        dependsOn(MapC13PacketPlayerAbilities)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenThis()
            thenCustomCheck {
                it is FieldInsnNode && it.owner == MapEntityPlayerSP.assumeMapped().name && it.name == MapEntityPlayerSP.mapSendQueue.assumeMapped().name
            }
            thenCustomCheck {
                it is TypeInsnNode && it.opcode == Opcodes.NEW && it.desc == MapC13PacketPlayerAbilities.assumeMapped().name
            }
            thenOpcodeCheck(Opcodes.DUP)
            thenThis()
            thenGroup("field") {
                thenCustomCheck {
                    it is FieldInsnNode && it.owner == MapEntityPlayerSP.assumeMapped().name && it.opcode == Opcodes.GETFIELD
                }
            }
        }
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()){
                MapEntityPlayer.map(getClass(MapEntityPlayerSP)!!.node.superName)
                MapEntityPlayerSP.mapSendPlayerAbilities.map(method)
                MapEntityPlayer.mapCapabilities.map(matcher.groupAsFieldInsnNode("field"))
                MapPlayerCapabilities.map(ASMUtils.descTypeToJvmType(matcher.groupAsFieldInsnNode("field").desc))
            }
        }
        assertMapped(MapPlayerCapabilities)
        return false
    }

    override fun start() {
        onlyProcess(MapEntityPlayerSP)
    }

    override fun jobDone(): Boolean {
        return MapPlayerCapabilities.isMapped()
    }

}