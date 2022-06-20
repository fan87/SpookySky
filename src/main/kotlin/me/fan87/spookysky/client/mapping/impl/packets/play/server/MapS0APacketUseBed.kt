
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS0APacketUseBed: PacketMapping<S0APacketUseBed>() {
    override fun getWrapperClass(): Class<S0APacketUseBed> {
        return S0APacketUseBed::class.java
    }

    override val humanReadableName: String
        get() = "S0APacketUseBed"
    override val id: Int
        get() = 0x0A
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S0APacketUseBed protected constructor(original: Any): Packet(original) {

}
