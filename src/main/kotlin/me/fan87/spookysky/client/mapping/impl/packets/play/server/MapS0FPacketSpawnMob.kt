
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS0FPacketSpawnMob: PacketMapping<S0FPacketSpawnMob>() {
    override fun getWrapperClass(): Class<S0FPacketSpawnMob> {
        return S0FPacketSpawnMob::class.java
    }

    override val humanReadableName: String
        get() = "S0FPacketSpawnMob"
    override val id: Int
        get() = 0x0F
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S0FPacketSpawnMob protected constructor(original: Any): Packet(original) {

}
