
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS31PacketWindowProperty: PacketMapping<S31PacketWindowProperty>() {
    override fun getWrapperClass(): Class<S31PacketWindowProperty> {
        return S31PacketWindowProperty::class.java
    }

    override val humanReadableName: String
        get() = "S31PacketWindowProperty"
    override val id: Int
        get() = 49
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S31PacketWindowProperty protected constructor(original: Any): Packet(original) {

}
