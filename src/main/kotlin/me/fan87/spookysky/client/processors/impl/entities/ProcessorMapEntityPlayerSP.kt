package me.fan87.spookysky.client.processors.impl.entities

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityLivingBase
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.packets.MapNetHandlerPlayClient
import me.fan87.spookysky.client.mapping.impl.packets.MapPacket
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.play.client.MapC0APacketAnimation
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.getJvmTypeName
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.TypeInsnNode

class ProcessorMapEntityPlayerSP: Processor("Map EntityPlayerSP") {

    init {
        dependsOn(MapC0APacketAnimation)
    }

    val pattern = RegbexPattern {
        thenGroup("sendQueue") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenOpcodeCheck(Opcodes.NEW)
        thenOpcodeCheck(Opcodes.DUP)
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenCustomCheck { it is LdcInsnNode && it.cst == -999.0 }
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenOpcodeCheck(Opcodes.INVOKESPECIAL)
        thenGroup("addToSendQueue") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
    }

    val swingPattern = RegbexPattern {
        thenCustomCheck { it is TypeInsnNode && it.desc == MapC0APacketAnimation.assumeMapped().name }
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapEntityPlayerSP.map(clazz.node.name)
                MapEntityPlayerSP.mapOnUpdateWalkingPlayer.map(method)
                MapEntityPlayerSP.mapSendQueue.map(matcher.groupAsFieldInsnNode("sendQueue"))
                for (method in clazz.node.methods) {
                    if (swingPattern.matcher(method).next()) {
                        MapEntityLivingBase.mapSwingItem.map(method)
                    }
                }
                MapNetHandlerPlayClient.map(matcher.groupAsMethodInsnNode("addToSendQueue").owner)
                MapNetHandlerPlayClient.mapAddToSendQueue.map(matcher.groupAsMethodInsnNode("addToSendQueue"))
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.mapThePlayer.isMapped()
    }
}