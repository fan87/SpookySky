
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS2BPacketChangeGameState: PacketMapping<S2BPacketChangeGameState>() {
    override fun getWrapperClass(): Class<S2BPacketChangeGameState> {
        return S2BPacketChangeGameState::class.java
    }

    override val humanReadableName: String
        get() = "S2BPacketChangeGameState"
    override val id: Int
        get() = 0x2B
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S2BPacketChangeGameState protected constructor(original: Any): Packet(original) {

}
