
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS12PacketEntityVelocity: PacketMapping<S12PacketEntityVelocity>() {
    override fun getWrapperClass(): Class<S12PacketEntityVelocity> {
        return S12PacketEntityVelocity::class.java
    }

    override val humanReadableName: String
        get() = "S12PacketEntityVelocity"
    override val id: Int
        get() = 0x12
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S12PacketEntityVelocity protected constructor(original: Any): Packet(original) {

}
