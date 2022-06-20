
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS41PacketServerDifficulty: PacketMapping<S41PacketServerDifficulty>() {
    override fun getWrapperClass(): Class<S41PacketServerDifficulty> {
        return S41PacketServerDifficulty::class.java
    }

    override val humanReadableName: String
        get() = "S41PacketServerDifficulty"
    override val id: Int
        get() = 65
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S41PacketServerDifficulty protected constructor(original: Any): Packet(original) {

}
