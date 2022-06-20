package me.fan87.spookysky.client.mapping.impl.packets.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.impl.packets.Packet

object MapC03PacketPlayer: ClassMapping<C06PacketPlayerPosLook>() {
    override fun getWrapperClass(): Class<C06PacketPlayerPosLook> {
        return C06PacketPlayerPosLook::class.java
    }

    override val humanReadableName: String
        get() = "C03PacketPlayer"
}

open class C03PacketPlayer protected constructor(original: Any) : Packet(original) {

}