
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS29PacketSoundEffect: PacketMapping<S29PacketSoundEffect>() {
    override fun getWrapperClass(): Class<S29PacketSoundEffect> {
        return S29PacketSoundEffect::class.java
    }

    override val humanReadableName: String
        get() = "S29PacketSoundEffect"
    override val id: Int
        get() = 0x29
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S29PacketSoundEffect protected constructor(original: Any): Packet(original) {

}
