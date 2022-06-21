package me.fan87.spookysky.client.mapping.impl.packets

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapPacket: ClassMapping<Packet>() {
    override fun getWrapperClass(): Class<Packet> {
        return Packet::class.java
    }

    override val humanReadableName: String
        get() = "Packet"

    val mapReadPacketData = MethodMapping<Unit, Packet>(this, "readPacketData(PacketBuffer)")
    val mapWritePacketData = MethodMapping<Unit, Packet>(this, "writePacketData(PacketBuffer)")

}

open class Packet protected constructor(original: Any) : WrapperClass(original) {


}