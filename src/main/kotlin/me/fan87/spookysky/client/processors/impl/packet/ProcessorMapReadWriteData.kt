package me.fan87.spookysky.client.processors.impl.packet

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.packets.MapPacket
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodInsnNode

class ProcessorMapReadWriteData: Processor("Map Packet.read/writeData") {

    init {
        dependsOn(MapPacket)
    }

    val packetSent = RegbexPattern {
        thenLdcStringEqual("PACKET_SENT")
    }

    val packetReceived = RegbexPattern {
        thenLdcStringEqual("PACKET_RECEIVED")
    }
    val after = RegbexPattern {
        thenCustomCheck { it is MethodInsnNode && it.opcode == Opcodes.INVOKEINTERFACE && it.owner == MapPacket.assumeMapped().name }
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (packetSent.matcher(method).next()) {
                val matcher = after.matcher(method)
                assertTrue(matcher.next())
                MapPacket.mapWritePacketData.map(matcher.group()[0] as MethodInsnNode)
            } else if (packetReceived.matcher(method).next()) {
                val matcher = after.matcher(method)
                assertTrue(matcher.next())
                MapPacket.mapReadPacketData.map(matcher.group()[0] as MethodInsnNode)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapPacket.mapReadPacketData.isMapped() && MapPacket.mapWritePacketData.isMapped()
    }
}