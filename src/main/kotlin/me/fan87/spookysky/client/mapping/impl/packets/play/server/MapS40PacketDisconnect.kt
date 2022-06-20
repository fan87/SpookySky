
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS40PacketDisconnect: PacketMapping<S40PacketDisconnect>() {
    override fun getWrapperClass(): Class<S40PacketDisconnect> {
        return S40PacketDisconnect::class.java
    }

    override val humanReadableName: String
        get() = "S40PacketDisconnect"
    override val id: Int
        get() = 0x40
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S40PacketDisconnect protected constructor(original: Any): Packet(original) {

}
