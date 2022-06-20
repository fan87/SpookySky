
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC09PacketHeldItemChange: PacketMapping<C09PacketHeldItemChange>() {
    override fun getWrapperClass(): Class<C09PacketHeldItemChange> {
        return C09PacketHeldItemChange::class.java
    }

    override val humanReadableName: String
        get() = "C09PacketHeldItemChange"
    override val id: Int
        get() = 9
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C09PacketHeldItemChange protected constructor(original: Any): Packet(original) {

}
