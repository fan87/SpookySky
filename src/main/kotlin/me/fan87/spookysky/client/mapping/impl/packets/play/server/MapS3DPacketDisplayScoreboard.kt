
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS3DPacketDisplayScoreboard: PacketMapping<S3DPacketDisplayScoreboard>() {
    override fun getWrapperClass(): Class<S3DPacketDisplayScoreboard> {
        return S3DPacketDisplayScoreboard::class.java
    }

    override val humanReadableName: String
        get() = "S3DPacketDisplayScoreboard"
    override val id: Int
        get() = 61
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S3DPacketDisplayScoreboard protected constructor(original: Any): Packet(original) {

}
