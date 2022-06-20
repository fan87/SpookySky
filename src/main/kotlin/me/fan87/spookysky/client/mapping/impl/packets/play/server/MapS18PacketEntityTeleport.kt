
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS18PacketEntityTeleport: PacketMapping<S18PacketEntityTeleport>() {
    override fun getWrapperClass(): Class<S18PacketEntityTeleport> {
        return S18PacketEntityTeleport::class.java
    }

    override val humanReadableName: String
        get() = "S18PacketEntityTeleport"
    override val id: Int
        get() = 0x18
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S18PacketEntityTeleport protected constructor(original: Any): Packet(original) {

}
