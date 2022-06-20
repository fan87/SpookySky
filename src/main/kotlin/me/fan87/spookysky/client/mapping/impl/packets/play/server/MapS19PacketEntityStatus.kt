
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS19PacketEntityStatus: PacketMapping<S19PacketEntityStatus>() {
    override fun getWrapperClass(): Class<S19PacketEntityStatus> {
        return S19PacketEntityStatus::class.java
    }

    override val humanReadableName: String
        get() = "S19PacketEntityStatus"
    override val id: Int
        get() = 0x19
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S19PacketEntityStatus protected constructor(original: Any): Packet(original) {

}
