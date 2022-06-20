
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS2FPacketSetSlot: PacketMapping<S2FPacketSetSlot>() {
    override fun getWrapperClass(): Class<S2FPacketSetSlot> {
        return S2FPacketSetSlot::class.java
    }

    override val humanReadableName: String
        get() = "S2FPacketSetSlot"
    override val id: Int
        get() = 47
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S2FPacketSetSlot protected constructor(original: Any): Packet(original) {

}
