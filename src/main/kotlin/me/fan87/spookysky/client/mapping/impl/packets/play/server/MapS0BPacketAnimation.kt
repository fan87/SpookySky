
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS0BPacketAnimation: PacketMapping<S0BPacketAnimation>() {
    override fun getWrapperClass(): Class<S0BPacketAnimation> {
        return S0BPacketAnimation::class.java
    }

    override val humanReadableName: String
        get() = "S0BPacketAnimation"
    override val id: Int
        get() = 11
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S0BPacketAnimation protected constructor(original: Any): Packet(original) {

}
