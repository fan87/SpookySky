
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS17PacketEntityLookMove: PacketMapping<S17PacketEntityLookMove>() {
    override fun getWrapperClass(): Class<S17PacketEntityLookMove> {
        return S17PacketEntityLookMove::class.java
    }

    override val humanReadableName: String
        get() = "S17PacketEntityLookMove"
    override val id: Int
        get() = 0x17
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S17PacketEntityLookMove protected constructor(original: Any): Packet(original) {

}
