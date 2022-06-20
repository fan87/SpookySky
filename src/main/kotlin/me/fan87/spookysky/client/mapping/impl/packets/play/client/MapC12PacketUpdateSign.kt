
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC12PacketUpdateSign: PacketMapping<C12PacketUpdateSign>() {
    override fun getWrapperClass(): Class<C12PacketUpdateSign> {
        return C12PacketUpdateSign::class.java
    }

    override val humanReadableName: String
        get() = "C12PacketUpdateSign"
    override val id: Int
        get() = 18
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C12PacketUpdateSign protected constructor(original: Any): Packet(original) {

}
