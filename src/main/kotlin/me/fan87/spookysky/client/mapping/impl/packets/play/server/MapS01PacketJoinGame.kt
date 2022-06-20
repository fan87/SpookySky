
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS01PacketJoinGame: PacketMapping<S01PacketJoinGame>() {
    override fun getWrapperClass(): Class<S01PacketJoinGame> {
        return S01PacketJoinGame::class.java
    }

    override val humanReadableName: String
        get() = "S01PacketJoinGame"
    override val id: Int
        get() = 0x01
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S01PacketJoinGame protected constructor(original: Any): Packet(original) {

}
