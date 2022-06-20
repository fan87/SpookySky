
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS25PacketBlockBreakAnim: PacketMapping<S25PacketBlockBreakAnim>() {
    override fun getWrapperClass(): Class<S25PacketBlockBreakAnim> {
        return S25PacketBlockBreakAnim::class.java
    }

    override val humanReadableName: String
        get() = "S25PacketBlockBreakAnim"
    override val id: Int
        get() = 0x25
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S25PacketBlockBreakAnim protected constructor(original: Any): Packet(original) {

}
