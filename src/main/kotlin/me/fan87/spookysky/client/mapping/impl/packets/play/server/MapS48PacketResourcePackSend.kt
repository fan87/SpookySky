
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS48PacketResourcePackSend: PacketMapping<S48PacketResourcePackSend>() {
    override fun getWrapperClass(): Class<S48PacketResourcePackSend> {
        return S48PacketResourcePackSend::class.java
    }

    override val humanReadableName: String
        get() = "S48PacketResourcePackSend"
    override val id: Int
        get() = 72
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S48PacketResourcePackSend protected constructor(original: Any): Packet(original) {

}
