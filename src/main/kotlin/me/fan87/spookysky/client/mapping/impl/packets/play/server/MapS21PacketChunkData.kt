
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS21PacketChunkData: PacketMapping<S21PacketChunkData>() {
    override fun getWrapperClass(): Class<S21PacketChunkData> {
        return S21PacketChunkData::class.java
    }

    override val humanReadableName: String
        get() = "S21PacketChunkData"
    override val id: Int
        get() = 33
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S21PacketChunkData protected constructor(original: Any): Packet(original) {

}
