package me.fan87.spookysky.client.processors.impl.packet

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.impl.packets.MapPacket
import me.fan87.spookysky.client.mapping.impl.packets.MapPacketBuffer
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.getMethod
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodInsnNode
import java.io.File

class ProcessorMapPacketPlayMembers: Processor("Map PacketPlay Members") {

    init {
        dependsOn(MapPacketBuffer)
        dependsOn(MapPacket)
        dependsOn(MapPacket.mapReadPacketData)
    }

    override fun start() {
        for (mapping in mappingsManager.mappings) {
            if (mapping is PacketMapping<*>) {
                if (mapping.mode == PacketSource.PLAY_SERVER || mapping.mode == PacketSource.PLAY_CLIENT) {
                    onlyProcess(mapping)
                    toMap++
                }
            }
        }
    }

    var toMap = 0
    var mapped = 0

    override fun process(clazz: LoadedClass): Boolean {
        mapped++
        for (mapping in mappingsManager.mappings) {
            if (mapping is PacketMapping<*>) {
                if (mapping.assumeMapped().name == clazz.name) {
                    val method = clazz.node.getMethod(MapPacket.mapReadPacketData)
                    val matcher = RegbexPattern {
                        thenThis()
                        thenVarLoadNode(1)
                        thenLazyAmountOf(0..3) {
                            thenAny() // So it would match if it's readStringFromBuffer (has an int as argument, and this should push it)
                        }
                        thenCustomCheck { it.opcode == Opcodes.INVOKEVIRTUAL && it is MethodInsnNode && it.owner == MapPacketBuffer.assumeMapped().name }
                        thenLazyAnyAmountOf {
                            thenAny() // So it would match if it's a boolean value (It has `!= 0` in the end)
                        }
                        thenGroup("field") {
                            thenOpcodeCheck(Opcodes.PUTFIELD)
                        }
                    }.matcher(method)
                    for (fieldMapping in mapping.getDataOrder()) {
                        if (!matcher.next()) {
                            val output = File(System.getProperty("java.io.tmprdir"), mapping.getWrapperClass().simpleName+ ".class")
                            output.writeBytes(ASMUtils.writeClass(clazz.node))
                            throw IllegalStateException("Couldn't find ${fieldMapping.humanReadableName} (Defined at ${mapping.humanReadableName}). The class has been dumped at ${output.canonicalPath}")
                        }
                        fieldMapping.map(matcher.groupAsFieldInsnNode("field"))
                    }
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return mapped == toMap
    }
}