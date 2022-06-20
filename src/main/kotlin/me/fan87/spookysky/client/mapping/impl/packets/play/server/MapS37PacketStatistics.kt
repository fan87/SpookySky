
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS37PacketStatistics: PacketMapping<S37PacketStatistics>() {
    override fun getWrapperClass(): Class<S37PacketStatistics> {
        return S37PacketStatistics::class.java
    }

    override val humanReadableName: String
        get() = "S37PacketStatistics"
    override val id: Int
        get() = 55
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S37PacketStatistics protected constructor(original: Any): Packet(original) {

}
