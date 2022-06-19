package me.fan87.spookysky.client.mapping.impl.packets

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.client.C06PacketPlayerPosLook

object MapPacket: ClassMapping<Packet>() {
    override val humanReadableName: String
        get() = "Packet"
}

open class Packet(original: Any) : WrapperClass(original) {

}