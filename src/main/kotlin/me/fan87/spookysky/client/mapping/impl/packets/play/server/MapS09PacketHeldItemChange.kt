
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS09PacketHeldItemChange: PacketMapping<S09PacketHeldItemChange>() {
    override fun getWrapperClass(): Class<S09PacketHeldItemChange> {
        return S09PacketHeldItemChange::class.java
    }

    override val humanReadableName: String
        get() = "S09PacketHeldItemChange"
    override val id: Int
        get() = 0x09
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S09PacketHeldItemChange protected constructor(original: Any): Packet(original) {

}
