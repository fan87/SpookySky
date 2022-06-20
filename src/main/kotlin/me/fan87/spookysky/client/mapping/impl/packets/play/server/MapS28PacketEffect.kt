
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS28PacketEffect: PacketMapping<S28PacketEffect>() {
    override fun getWrapperClass(): Class<S28PacketEffect> {
        return S28PacketEffect::class.java
    }

    override val humanReadableName: String
        get() = "S28PacketEffect"
    override val id: Int
        get() = 0x28
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S28PacketEffect protected constructor(original: Any): Packet(original) {

}
