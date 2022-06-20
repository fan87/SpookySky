
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS33PacketUpdateSign: PacketMapping<S33PacketUpdateSign>() {
    override fun getWrapperClass(): Class<S33PacketUpdateSign> {
        return S33PacketUpdateSign::class.java
    }

    override val humanReadableName: String
        get() = "S33PacketUpdateSign"
    override val id: Int
        get() = 0x33
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S33PacketUpdateSign protected constructor(original: Any): Packet(original) {

}