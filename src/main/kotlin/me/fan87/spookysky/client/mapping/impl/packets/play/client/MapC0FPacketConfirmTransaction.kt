
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC0FPacketConfirmTransaction: PacketMapping<C0FPacketConfirmTransaction>() {
    override fun getWrapperClass(): Class<C0FPacketConfirmTransaction> {
        return C0FPacketConfirmTransaction::class.java
    }

    override val humanReadableName: String
        get() = "C0FPacketConfirmTransaction"
    override val id: Int
        get() = 0x0F
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C0FPacketConfirmTransaction protected constructor(original: Any): Packet(original) {

}
