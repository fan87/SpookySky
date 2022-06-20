
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS26PacketMapChunkBulk: PacketMapping<S26PacketMapChunkBulk>() {
    override fun getWrapperClass(): Class<S26PacketMapChunkBulk> {
        return S26PacketMapChunkBulk::class.java
    }

    override val humanReadableName: String
        get() = "S26PacketMapChunkBulk"
    override val id: Int
        get() = 38
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S26PacketMapChunkBulk protected constructor(original: Any): Packet(original) {

}
