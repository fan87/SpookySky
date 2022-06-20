
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC0APacketAnimation: PacketMapping<C0APacketAnimation>() {
    override fun getWrapperClass(): Class<C0APacketAnimation> {
        return C0APacketAnimation::class.java
    }

    override val humanReadableName: String
        get() = "C0APacketAnimation"
    override val id: Int
        get() = 0x0A
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C0APacketAnimation protected constructor(original: Any): Packet(original) {

}
