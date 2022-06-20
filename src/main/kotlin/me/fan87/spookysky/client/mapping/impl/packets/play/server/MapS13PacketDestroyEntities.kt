
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS13PacketDestroyEntities: PacketMapping<S13PacketDestroyEntities>() {
    override fun getWrapperClass(): Class<S13PacketDestroyEntities> {
        return S13PacketDestroyEntities::class.java
    }

    override val humanReadableName: String
        get() = "S13PacketDestroyEntities"
    override val id: Int
        get() = 0x13
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S13PacketDestroyEntities protected constructor(original: Any): Packet(original) {

}
