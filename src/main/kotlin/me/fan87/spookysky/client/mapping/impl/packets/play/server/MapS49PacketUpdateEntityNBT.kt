
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS49PacketUpdateEntityNBT: PacketMapping<S49PacketUpdateEntityNBT>() {
    override fun getWrapperClass(): Class<S49PacketUpdateEntityNBT> {
        return S49PacketUpdateEntityNBT::class.java
    }

    override val humanReadableName: String
        get() = "S49PacketUpdateEntityNBT"
    override val id: Int
        get() = 0x49
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S49PacketUpdateEntityNBT protected constructor(original: Any): Packet(original) {

}
