
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS27PacketExplosion: PacketMapping<S27PacketExplosion>() {
    override fun getWrapperClass(): Class<S27PacketExplosion> {
        return S27PacketExplosion::class.java
    }

    override val humanReadableName: String
        get() = "S27PacketExplosion"
    override val id: Int
        get() = 0x27
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S27PacketExplosion protected constructor(original: Any): Packet(original) {

}
