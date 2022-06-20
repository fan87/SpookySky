
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC0BPacketEntityAction: PacketMapping<C0BPacketEntityAction>() {
    override fun getWrapperClass(): Class<C0BPacketEntityAction> {
        return C0BPacketEntityAction::class.java
    }

    override val humanReadableName: String
        get() = "C0BPacketEntityAction"
    override val id: Int
        get() = 0x0B
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C0BPacketEntityAction protected constructor(original: Any): Packet(original) {

}
