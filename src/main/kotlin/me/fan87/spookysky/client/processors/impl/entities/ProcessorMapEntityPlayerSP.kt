package me.fan87.spookysky.client.processors.impl.entities

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.mapping.impl.packets.MapPacket
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.client.MapC03PacketPlayer
import me.fan87.spookysky.client.mapping.impl.packets.client.MapC06PacketPlayerPosLook
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.getJvmTypeName
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.TypeInsnNode

class ProcessorMapEntityPlayerSP: Processor("Map EntityPlayerSP") {


    val pattern = RegbexPattern {
        thenGroup("C06") {
            thenOpcodeCheck(Opcodes.NEW)
        }
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
        thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapEntityPlayerSP.map(clazz.node.name)
                MapEntityPlayerSP.mapOnUpdateWalkingPlayer.map(method)
                MapC06PacketPlayerPosLook.map(matcher.group("C06")!![0] as TypeInsnNode)
                MapC03PacketPlayer.map(MapC06PacketPlayerPosLook.getJavaClass().superclass.getJvmTypeName())
                MapPacket.map(MapC03PacketPlayer.getJavaClass().interfaces[0].getJvmTypeName())
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.mapThePlayer.isMapped()
    }
}