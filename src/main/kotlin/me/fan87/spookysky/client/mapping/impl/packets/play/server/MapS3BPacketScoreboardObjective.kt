
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS3BPacketScoreboardObjective: PacketMapping<S3BPacketScoreboardObjective>() {
    override fun getWrapperClass(): Class<S3BPacketScoreboardObjective> {
        return S3BPacketScoreboardObjective::class.java
    }

    override val humanReadableName: String
        get() = "S3BPacketScoreboardObjective"
    override val id: Int
        get() = 59
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S3BPacketScoreboardObjective protected constructor(original: Any): Packet(original) {

}
