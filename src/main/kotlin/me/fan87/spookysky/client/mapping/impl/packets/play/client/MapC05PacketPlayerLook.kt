
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC05PacketPlayerLook: PacketMapping<C05PacketPlayerLook>() {
    override fun getWrapperClass(): Class<C05PacketPlayerLook> {
        return C05PacketPlayerLook::class.java
    }

    override val humanReadableName: String
        get() = "C05PacketPlayerLook"
    override val id: Int
        get() = 0x05
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C05PacketPlayerLook protected constructor(original: Any): Packet(original) {

}
