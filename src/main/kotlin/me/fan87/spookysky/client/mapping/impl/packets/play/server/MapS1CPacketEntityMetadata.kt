
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS1CPacketEntityMetadata: PacketMapping<S1CPacketEntityMetadata>() {
    override fun getWrapperClass(): Class<S1CPacketEntityMetadata> {
        return S1CPacketEntityMetadata::class.java
    }

    override val humanReadableName: String
        get() = "S1CPacketEntityMetadata"
    override val id: Int
        get() = 0x1C
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S1CPacketEntityMetadata protected constructor(original: Any): Packet(original) {

}
