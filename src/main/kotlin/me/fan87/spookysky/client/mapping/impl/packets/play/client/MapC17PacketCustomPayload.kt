
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC17PacketCustomPayload: PacketMapping<C17PacketCustomPayload>() {
    override fun getWrapperClass(): Class<C17PacketCustomPayload> {
        return C17PacketCustomPayload::class.java
    }

    override val humanReadableName: String
        get() = "C17PacketCustomPayload"
    override val id: Int
        get() = 23
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C17PacketCustomPayload protected constructor(original: Any): Packet(original) {

}
