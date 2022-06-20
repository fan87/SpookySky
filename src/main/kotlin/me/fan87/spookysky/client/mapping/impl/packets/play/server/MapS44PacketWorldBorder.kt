
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS44PacketWorldBorder: PacketMapping<S44PacketWorldBorder>() {
    override fun getWrapperClass(): Class<S44PacketWorldBorder> {
        return S44PacketWorldBorder::class.java
    }

    override val humanReadableName: String
        get() = "S44PacketWorldBorder"
    override val id: Int
        get() = 0x44
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S44PacketWorldBorder protected constructor(original: Any): Packet(original) {

}
