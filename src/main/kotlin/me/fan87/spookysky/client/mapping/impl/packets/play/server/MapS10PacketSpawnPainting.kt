
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS10PacketSpawnPainting: PacketMapping<S10PacketSpawnPainting>() {
    override fun getWrapperClass(): Class<S10PacketSpawnPainting> {
        return S10PacketSpawnPainting::class.java
    }

    override val humanReadableName: String
        get() = "S10PacketSpawnPainting"
    override val id: Int
        get() = 16
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S10PacketSpawnPainting protected constructor(original: Any): Packet(original) {

}
