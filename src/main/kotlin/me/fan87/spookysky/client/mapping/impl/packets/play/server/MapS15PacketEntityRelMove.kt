
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS15PacketEntityRelMove: PacketMapping<S15PacketEntityRelMove>() {
    override fun getWrapperClass(): Class<S15PacketEntityRelMove> {
        return S15PacketEntityRelMove::class.java
    }

    override val humanReadableName: String
        get() = "S15PacketEntityRelMove"
    override val id: Int
        get() = 21
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S15PacketEntityRelMove protected constructor(original: Any): Packet(original) {

}
