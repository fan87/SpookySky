
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS0CPacketSpawnPlayer: PacketMapping<S0CPacketSpawnPlayer>() {
    override fun getWrapperClass(): Class<S0CPacketSpawnPlayer> {
        return S0CPacketSpawnPlayer::class.java
    }

    override val humanReadableName: String
        get() = "S0CPacketSpawnPlayer"
    override val id: Int
        get() = 12
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S0CPacketSpawnPlayer protected constructor(original: Any): Packet(original) {

}
