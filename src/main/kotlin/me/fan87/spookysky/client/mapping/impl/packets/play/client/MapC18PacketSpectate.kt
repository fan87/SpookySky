
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC18PacketSpectate: PacketMapping<C18PacketSpectate>() {
    override fun getWrapperClass(): Class<C18PacketSpectate> {
        return C18PacketSpectate::class.java
    }

    override val humanReadableName: String
        get() = "C18PacketSpectate"
    override val id: Int
        get() = 0x18
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C18PacketSpectate protected constructor(original: Any): Packet(original) {

}
