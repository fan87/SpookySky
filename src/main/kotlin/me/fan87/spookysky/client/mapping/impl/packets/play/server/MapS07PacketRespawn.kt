
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS07PacketRespawn: PacketMapping<S07PacketRespawn>() {
    override fun getWrapperClass(): Class<S07PacketRespawn> {
        return S07PacketRespawn::class.java
    }

    override val humanReadableName: String
        get() = "S07PacketRespawn"
    override val id: Int
        get() = 7
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S07PacketRespawn protected constructor(original: Any): Packet(original) {

}
