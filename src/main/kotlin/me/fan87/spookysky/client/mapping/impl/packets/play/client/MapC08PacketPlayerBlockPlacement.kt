
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC08PacketPlayerBlockPlacement: PacketMapping<C08PacketPlayerBlockPlacement>() {
    override fun getWrapperClass(): Class<C08PacketPlayerBlockPlacement> {
        return C08PacketPlayerBlockPlacement::class.java
    }

    override val humanReadableName: String
        get() = "C08PacketPlayerBlockPlacement"
    override val id: Int
        get() = 8
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C08PacketPlayerBlockPlacement protected constructor(original: Any): Packet(original) {

}
