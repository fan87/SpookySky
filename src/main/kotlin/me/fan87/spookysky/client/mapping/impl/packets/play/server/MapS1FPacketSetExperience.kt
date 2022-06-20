
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS1FPacketSetExperience: PacketMapping<S1FPacketSetExperience>() {
    override fun getWrapperClass(): Class<S1FPacketSetExperience> {
        return S1FPacketSetExperience::class.java
    }

    override val humanReadableName: String
        get() = "S1FPacketSetExperience"
    override val id: Int
        get() = 0x1F
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S1FPacketSetExperience protected constructor(original: Any): Packet(original) {

}
