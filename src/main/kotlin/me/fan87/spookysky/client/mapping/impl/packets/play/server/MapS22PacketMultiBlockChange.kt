
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS22PacketMultiBlockChange: PacketMapping<S22PacketMultiBlockChange>() {
    override fun getWrapperClass(): Class<S22PacketMultiBlockChange> {
        return S22PacketMultiBlockChange::class.java
    }

    override val humanReadableName: String
        get() = "S22PacketMultiBlockChange"
    override val id: Int
        get() = 0x22
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S22PacketMultiBlockChange protected constructor(original: Any): Packet(original) {

}
