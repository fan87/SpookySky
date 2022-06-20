
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC07PacketPlayerDigging: PacketMapping<C07PacketPlayerDigging>() {
    override fun getWrapperClass(): Class<C07PacketPlayerDigging> {
        return C07PacketPlayerDigging::class.java
    }

    override val humanReadableName: String
        get() = "C07PacketPlayerDigging"
    override val id: Int
        get() = 0x07
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C07PacketPlayerDigging protected constructor(original: Any): Packet(original) {

}
