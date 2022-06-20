
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS06PacketUpdateHealth: PacketMapping<S06PacketUpdateHealth>() {
    override fun getWrapperClass(): Class<S06PacketUpdateHealth> {
        return S06PacketUpdateHealth::class.java
    }

    override val humanReadableName: String
        get() = "S06PacketUpdateHealth"
    override val id: Int
        get() = 0x06
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S06PacketUpdateHealth protected constructor(original: Any): Packet(original) {

}
