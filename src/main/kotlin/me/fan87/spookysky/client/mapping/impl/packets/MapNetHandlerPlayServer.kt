package me.fan87.spookysky.client.mapping.impl.packets


import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.events.PacketReceivedEvent
import me.fan87.spookysky.client.events.events.PacketSentEvent
import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapNetHandlerPlayServer : ClassMapping<NetHandlerPlayServer>() {
    override fun getWrapperClass(): Class<NetHandlerPlayServer> {
        return NetHandlerPlayServer::class.java
    }

    override val humanReadableName: String
        get() = "NetHandlerPlayServer"

    val mapSendPacket = MethodMapping<Unit, NetHandlerPlayServer>(this, "sendPacket(Packet)")
}

open class NetHandlerPlayServer protected constructor(original: Any) : WrapperClass(original) {




}

