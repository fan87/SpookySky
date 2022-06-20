
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS1DPacketEntityEffect: PacketMapping<S1DPacketEntityEffect>() {
    override fun getWrapperClass(): Class<S1DPacketEntityEffect> {
        return S1DPacketEntityEffect::class.java
    }

    override val humanReadableName: String
        get() = "S1DPacketEntityEffect"
    override val id: Int
        get() = 0x1D
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S1DPacketEntityEffect protected constructor(original: Any): Packet(original) {

}
