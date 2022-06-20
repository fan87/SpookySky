
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS3FPacketCustomPayload: PacketMapping<S3FPacketCustomPayload>() {
    override fun getWrapperClass(): Class<S3FPacketCustomPayload> {
        return S3FPacketCustomPayload::class.java
    }

    override val humanReadableName: String
        get() = "S3FPacketCustomPayload"
    override val id: Int
        get() = 0x3F
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S3FPacketCustomPayload protected constructor(original: Any): Packet(original) {

}
