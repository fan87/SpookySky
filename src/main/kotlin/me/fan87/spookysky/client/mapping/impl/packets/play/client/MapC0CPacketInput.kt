
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC0CPacketInput: PacketMapping<C0CPacketInput>() {
    override fun getWrapperClass(): Class<C0CPacketInput> {
        return C0CPacketInput::class.java
    }

    override val humanReadableName: String
        get() = "C0CPacketInput"
    override val id: Int
        get() = 12
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C0CPacketInput protected constructor(original: Any): Packet(original) {

}
