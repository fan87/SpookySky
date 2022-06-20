
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS34PacketMaps: PacketMapping<S34PacketMaps>() {
    override fun getWrapperClass(): Class<S34PacketMaps> {
        return S34PacketMaps::class.java
    }

    override val humanReadableName: String
        get() = "S34PacketMaps"
    override val id: Int
        get() = 52
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S34PacketMaps protected constructor(original: Any): Packet(original) {

}
