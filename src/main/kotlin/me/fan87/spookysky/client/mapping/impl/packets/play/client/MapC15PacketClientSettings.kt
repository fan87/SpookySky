
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC15PacketClientSettings: PacketMapping<C15PacketClientSettings>() {
    override fun getWrapperClass(): Class<C15PacketClientSettings> {
        return C15PacketClientSettings::class.java
    }

    override val humanReadableName: String
        get() = "C15PacketClientSettings"
    override val id: Int
        get() = 0x15
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C15PacketClientSettings protected constructor(original: Any): Packet(original) {

}
