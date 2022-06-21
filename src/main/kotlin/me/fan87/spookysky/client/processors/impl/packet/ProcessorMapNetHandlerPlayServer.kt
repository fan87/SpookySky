package me.fan87.spookysky.client.processors.impl.packet

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.impl.packets.MapNetHandlerPlayServer
import me.fan87.spookysky.client.mapping.impl.packets.MapNetworkManager
import me.fan87.spookysky.client.mapping.impl.packets.MapPacket
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.getMethod
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsMethodInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapNetHandlerPlayServer: Processor("Map NetHandlerPlayServer") {

    init {
        dependsOn(MapPacket)
    }

    val pattern = RegbexPattern {
        thenLdcStringEqual("Invalid book tag!")
    }
    val patternAfterMatch = RegbexPattern {
        thenThis()
        thenOpcodeCheck(Opcodes.GETFIELD)
        thenVarLoadNode(1)
        thenGroup("method") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (pattern.matcher(method).next()) {
                MapNetHandlerPlayServer.map(clazz)
                for (method in clazz.node.methods) {
                    if (method.desc == "(${MapPacket.assumeMapped().getDescName()})V") {
                        val matcher = patternAfterMatch.matcher(method)
                        if (!matcher.next()) {
                            unsupportedClient("Couldn't match patternAfterMatch")
                        }
                        MapNetHandlerPlayServer.mapSendPacket.map(method)
                        MapNetworkManager.map(matcher.groupAsMethodInsnNode("method").owner)
                        MapNetworkManager.mapSendPacket.map(matcher.groupAsMethodInsnNode("method"))
                        MapNetworkManager.mapChannelRead0.map(SpookySky.INSTANCE.classes[MapNetworkManager.assumeMapped().name]!!.node.methods.first { it.name == "channelRead0" })
                        break
                    }
                }
                assertMapped(MapNetHandlerPlayServer.mapSendPacket)
                return false
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapNetHandlerPlayServer.isMapped()
    }
}