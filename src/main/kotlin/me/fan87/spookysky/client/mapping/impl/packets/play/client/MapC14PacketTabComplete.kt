
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC14PacketTabComplete: PacketMapping<C14PacketTabComplete>() {
    override fun getWrapperClass(): Class<C14PacketTabComplete> {
        return C14PacketTabComplete::class.java
    }

    override val humanReadableName: String
        get() = "C14PacketTabComplete"
    override val id: Int
        get() = 0x14
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C14PacketTabComplete protected constructor(original: Any): Packet(original) {

}
