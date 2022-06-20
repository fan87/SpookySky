
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC04PacketPlayerPosition: PacketMapping<C04PacketPlayerPosition>() {
    override fun getWrapperClass(): Class<C04PacketPlayerPosition> {
        return C04PacketPlayerPosition::class.java
    }

    override val humanReadableName: String
        get() = "C04PacketPlayerPosition"
    override val id: Int
        get() = 0x04
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C04PacketPlayerPosition protected constructor(original: Any): Packet(original) {

}