
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS46PacketSetCompressionLevel: PacketMapping<S46PacketSetCompressionLevel>() {
    override fun getWrapperClass(): Class<S46PacketSetCompressionLevel> {
        return S46PacketSetCompressionLevel::class.java
    }

    override val humanReadableName: String
        get() = "S46PacketSetCompressionLevel"
    override val id: Int
        get() = 70
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S46PacketSetCompressionLevel protected constructor(original: Any): Packet(original) {

}
