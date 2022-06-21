package me.fan87.spookysky.client.mapping.impl.packets


import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.events.PacketReceivedEvent
import me.fan87.spookysky.client.events.events.PacketSentEvent
import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapNetworkManager : ClassMapping<NetworkManager>() {
    override fun getWrapperClass(): Class<NetworkManager> {
        return NetworkManager::class.java
    }

    override val humanReadableName: String
        get() = "NetworkManager"

    val mapSendPacket = MethodMapping<Unit, NetworkManager>(this, "sendPacket(Packet)")
    val mapChannelRead0 = MethodMapping<Unit, NetworkManager>(this, "channelRead0(ChannelHandlerContext, Packet)")

}

open class NetworkManager protected constructor(original: Any) : WrapperClass(original) {

    fun sendPacket(packet: Packet) {
        MapNetworkManager.mapSendPacket.invoke(this, packet.original)
    }
    fun sendPacketNoEvent(packet: Packet) {
        PacketHandler.noEventPackets.add(packet.original)
        MapNetworkManager.mapSendPacket.invoke(this, packet.original)
    }

}



object PacketHandler {
    val noEventPackets = ArrayList<Any>()

    @JvmStatic
    fun onPacketReceive(packet: Any) {
        if (noEventPackets.contains(packet)) {
            noEventPackets.remove(packet)
        } else {
            SpookySky.INSTANCE.eventManager.post(PacketReceivedEvent(MappingsManager.getWrapped(packet)))
        }
    }
    @JvmStatic
    fun onPacketSent(packet: Any) {
        if (noEventPackets.contains(packet)) {
            noEventPackets.remove(packet)
        } else {
            SpookySky.INSTANCE.eventManager.post(PacketSentEvent(MappingsManager.getWrapped(packet)))
        }
    }

}