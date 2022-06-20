
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC03PacketPlayer: PacketMapping<C03PacketPlayer>() {
    override fun getWrapperClass(): Class<C03PacketPlayer> {
        return C03PacketPlayer::class.java
    }

    override val humanReadableName: String
        get() = "C03PacketPlayer"
    override val id: Int
        get() = 0x03
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C03PacketPlayer protected constructor(original: Any): Packet(original) {

}
