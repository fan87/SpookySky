
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC06PacketPlayerPosLook: PacketMapping<C06PacketPlayerPosLook>() {
    override fun getWrapperClass(): Class<C06PacketPlayerPosLook> {
        return C06PacketPlayerPosLook::class.java
    }

    override val humanReadableName: String
        get() = "C06PacketPlayerPosLook"
    override val id: Int
        get() = 6
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C06PacketPlayerPosLook protected constructor(original: Any): Packet(original) {

}
