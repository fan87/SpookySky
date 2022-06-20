
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS1BPacketEntityAttach: PacketMapping<S1BPacketEntityAttach>() {
    override fun getWrapperClass(): Class<S1BPacketEntityAttach> {
        return S1BPacketEntityAttach::class.java
    }

    override val humanReadableName: String
        get() = "S1BPacketEntityAttach"
    override val id: Int
        get() = 27
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S1BPacketEntityAttach protected constructor(original: Any): Packet(original) {

}
