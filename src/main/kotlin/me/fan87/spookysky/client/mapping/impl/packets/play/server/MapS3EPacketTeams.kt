
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS3EPacketTeams: PacketMapping<S3EPacketTeams>() {
    override fun getWrapperClass(): Class<S3EPacketTeams> {
        return S3EPacketTeams::class.java
    }

    override val humanReadableName: String
        get() = "S3EPacketTeams"
    override val id: Int
        get() = 62
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S3EPacketTeams protected constructor(original: Any): Packet(original) {

}
