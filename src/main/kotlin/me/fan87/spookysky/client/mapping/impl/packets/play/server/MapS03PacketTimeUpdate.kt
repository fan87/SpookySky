
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS03PacketTimeUpdate: PacketMapping<S03PacketTimeUpdate>() {
    override fun getWrapperClass(): Class<S03PacketTimeUpdate> {
        return S03PacketTimeUpdate::class.java
    }

    override val humanReadableName: String
        get() = "S03PacketTimeUpdate"
    override val id: Int
        get() = 3
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S03PacketTimeUpdate protected constructor(original: Any): Packet(original) {

}
