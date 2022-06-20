
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS02PacketChat: PacketMapping<S02PacketChat>() {
    override fun getWrapperClass(): Class<S02PacketChat> {
        return S02PacketChat::class.java
    }

    override val humanReadableName: String
        get() = "S02PacketChat"
    override val id: Int
        get() = 2
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S02PacketChat protected constructor(original: Any): Packet(original) {

}
