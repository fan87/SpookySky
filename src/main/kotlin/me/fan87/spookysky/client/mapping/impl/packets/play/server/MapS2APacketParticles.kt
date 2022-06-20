
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS2APacketParticles: PacketMapping<S2APacketParticles>() {
    override fun getWrapperClass(): Class<S2APacketParticles> {
        return S2APacketParticles::class.java
    }

    override val humanReadableName: String
        get() = "S2APacketParticles"
    override val id: Int
        get() = 42
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S2APacketParticles protected constructor(original: Any): Packet(original) {

}
