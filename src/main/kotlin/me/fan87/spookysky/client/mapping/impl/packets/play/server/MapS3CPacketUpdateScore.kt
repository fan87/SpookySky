
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS3CPacketUpdateScore: PacketMapping<S3CPacketUpdateScore>() {
    override fun getWrapperClass(): Class<S3CPacketUpdateScore> {
        return S3CPacketUpdateScore::class.java
    }

    override val humanReadableName: String
        get() = "S3CPacketUpdateScore"
    override val id: Int
        get() = 60
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S3CPacketUpdateScore protected constructor(original: Any): Packet(original) {

}
