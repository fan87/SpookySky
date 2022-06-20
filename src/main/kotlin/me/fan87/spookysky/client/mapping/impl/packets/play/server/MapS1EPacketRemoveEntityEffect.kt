
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS1EPacketRemoveEntityEffect: PacketMapping<S1EPacketRemoveEntityEffect>() {
    override fun getWrapperClass(): Class<S1EPacketRemoveEntityEffect> {
        return S1EPacketRemoveEntityEffect::class.java
    }

    override val humanReadableName: String
        get() = "S1EPacketRemoveEntityEffect"
    override val id: Int
        get() = 30
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S1EPacketRemoveEntityEffect protected constructor(original: Any): Packet(original) {

}
