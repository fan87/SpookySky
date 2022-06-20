
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS3APacketTabComplete: PacketMapping<S3APacketTabComplete>() {
    override fun getWrapperClass(): Class<S3APacketTabComplete> {
        return S3APacketTabComplete::class.java
    }

    override val humanReadableName: String
        get() = "S3APacketTabComplete"
    override val id: Int
        get() = 58
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S3APacketTabComplete protected constructor(original: Any): Packet(original) {

}
