
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS23PacketBlockChange: PacketMapping<S23PacketBlockChange>() {
    override fun getWrapperClass(): Class<S23PacketBlockChange> {
        return S23PacketBlockChange::class.java
    }

    override val humanReadableName: String
        get() = "S23PacketBlockChange"
    override val id: Int
        get() = 35
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S23PacketBlockChange protected constructor(original: Any): Packet(original) {

}
