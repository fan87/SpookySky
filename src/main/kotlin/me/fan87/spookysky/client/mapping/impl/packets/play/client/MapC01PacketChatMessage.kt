
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC01PacketChatMessage: PacketMapping<C01PacketChatMessage>() {
    override fun getWrapperClass(): Class<C01PacketChatMessage> {
        return C01PacketChatMessage::class.java
    }

    override val humanReadableName: String
        get() = "C01PacketChatMessage"
    override val id: Int
        get() = 1
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C01PacketChatMessage protected constructor(original: Any): Packet(original) {

}
