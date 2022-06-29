package me.fan87.spookysky.client.processors.impl.packet

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.impl.packets.MapPacket
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource
import me.fan87.spookysky.client.mapping.impl.packets.play.client.MapC00PacketKeepAlive
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.getMethod
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.LdcInsnNode

class ProcessorMapPacketPlayTypes: Processor("Map All Packet Types") {
    var playName: String? = null

    override fun process(clazz: LoadedClass): Boolean {
        if (clazz.node.name == playName) {
            val method = clazz.node.getMethod("<init>", "(Ljava/lang/String;II)V")
            var clientPackets: MutableMap<Int, PacketMapping<*>> = LinkedHashMap()
            var serverPackets: MutableMap<Int, PacketMapping<*>> = LinkedHashMap()
            for (mapping in mappingsManager.mappings) {
                if (mapping is PacketMapping<*>) {
                    if (mapping.mode == PacketSource.PLAY_CLIENT) {
                        clientPackets[mapping.id] = mapping
                    }
                    if (mapping.mode == PacketSource.PLAY_SERVER) {
                        serverPackets[mapping.id] = mapping
                    }
                }
            }
            clientPackets = clientPackets.toSortedMap(Comparator.comparingInt { it })
            serverPackets = serverPackets.toSortedMap(Comparator.comparingInt { it })
            val pattern = RegbexPattern {
                for (serverPacket in serverPackets) {
                    thenThis()
                    thenOpcodeCheck(Opcodes.GETSTATIC)
                    thenGroup(serverPacket.value.javaClass.name) {
                        thenOpcodeCheck(Opcodes.LDC)
                    }
                    thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
                    thenOpcodeCheck(Opcodes.POP)
                    thenLazyAmountOf(0..5) {
                        thenAny()
                    }
                }
                for (clientPacket in clientPackets) {
                    thenThis()
                    thenOpcodeCheck(Opcodes.GETSTATIC)
                    thenGroup(clientPacket.value.javaClass.name) {
                        thenOpcodeCheck(Opcodes.LDC)
                    }
                    thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
                    thenOpcodeCheck(Opcodes.POP)
                    thenLazyAmountOf(0..5) {
                        thenAny()
                    }
                }
            }
            val matcher = pattern.matcher(method)
            if (!matcher.next()) {
                unsupportedClient("Couldn't match find packet!")
            }
            for (serverPacket in serverPackets) {
                serverPacket.value.map(((matcher.group(serverPacket.value.javaClass.name)!![0] as LdcInsnNode).cst as Type).internalName)
            }
            for (clientPacket in clientPackets) {
                clientPacket.value.map(((matcher.group(clientPacket.value.javaClass.name)!![0] as LdcInsnNode).cst as Type).internalName)
            }
            MapPacket.map(MapC00PacketKeepAlive.getLoadedClass()!!.node.interfaces[0])
            processed = true
        } else {
            if (clazz.node.access and Opcodes.ACC_ENUM == 0) {
                return false
            }
            for (method in clazz.node.methods) {
                val pattern = RegbexPattern {
                    thenLdcStringEqual(" is already known to ID ")
                }
                if (pattern.matcher(method).next()) {

                    val init = clazz.node.getMethod("<clinit>", "()V")
                    val playPattern = RegbexPattern {
                        thenOpcodeCheck(Opcodes.LDC)
                        thenPushInt(1)
                        thenPushInt(0)
                        thenGroup("play") {
                            thenOpcodeCheck(Opcodes.INVOKESPECIAL)
                        }
                    }
                    val matcher = playPattern.matcher(init)
                    if (!matcher.next()) {
                        unsupportedClient("Couldn't find PLAY registry section")
                    }
                    playName = matcher.groupAsMethodInsnNode("play").owner

                }
            }
        }
        return false
    }

    var processed = false
    override fun jobDone(): Boolean {
        return processed
    }
}