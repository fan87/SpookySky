
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS47PacketPlayerListHeaderFooter: PacketMapping<S47PacketPlayerListHeaderFooter>() {
    override fun getWrapperClass(): Class<S47PacketPlayerListHeaderFooter> {
        return S47PacketPlayerListHeaderFooter::class.java
    }

    override val humanReadableName: String
        get() = "S47PacketPlayerListHeaderFooter"
    override val id: Int
        get() = 71
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S47PacketPlayerListHeaderFooter protected constructor(original: Any): Packet(original) {

}
