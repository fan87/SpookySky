
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC19PacketResourcePackStatus: PacketMapping<C19PacketResourcePackStatus>() {
    override fun getWrapperClass(): Class<C19PacketResourcePackStatus> {
        return C19PacketResourcePackStatus::class.java
    }

    override val humanReadableName: String
        get() = "C19PacketResourcePackStatus"
    override val id: Int
        get() = 0x19
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C19PacketResourcePackStatus protected constructor(original: Any): Packet(original) {

}
