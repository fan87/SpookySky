
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS05PacketSpawnPosition: PacketMapping<S05PacketSpawnPosition>() {
    override fun getWrapperClass(): Class<S05PacketSpawnPosition> {
        return S05PacketSpawnPosition::class.java
    }

    override val humanReadableName: String
        get() = "S05PacketSpawnPosition"
    override val id: Int
        get() = 0x05
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S05PacketSpawnPosition protected constructor(original: Any): Packet(original) {

}
