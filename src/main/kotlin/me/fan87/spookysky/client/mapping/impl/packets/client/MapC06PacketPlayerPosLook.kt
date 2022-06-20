package me.fan87.spookysky.client.mapping.impl.packets.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.impl.packets.Packet

object MapC06PacketPlayerPosLook: ClassMapping<C06PacketPlayerPosLook>() {
    override fun getWrapperClass(): Class<C06PacketPlayerPosLook> {
        return C06PacketPlayerPosLook::class.java
    }

    override val humanReadableName: String
        get() = "C06PacketPlayerPosLook"
}

class C06PacketPlayerPosLook protected constructor(original: Any) : C03PacketPlayer(original) {

}