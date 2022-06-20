
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC16PacketClientStatus: PacketMapping<C16PacketClientStatus>() {
    override fun getWrapperClass(): Class<C16PacketClientStatus> {
        return C16PacketClientStatus::class.java
    }

    override val humanReadableName: String
        get() = "C16PacketClientStatus"
    override val id: Int
        get() = 0x16
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C16PacketClientStatus protected constructor(original: Any): Packet(original) {

}
