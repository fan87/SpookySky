
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS30PacketWindowItems: PacketMapping<S30PacketWindowItems>() {
    override fun getWrapperClass(): Class<S30PacketWindowItems> {
        return S30PacketWindowItems::class.java
    }

    override val humanReadableName: String
        get() = "S30PacketWindowItems"
    override val id: Int
        get() = 48
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S30PacketWindowItems protected constructor(original: Any): Packet(original) {

}
