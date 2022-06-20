
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS20PacketEntityProperties: PacketMapping<S20PacketEntityProperties>() {
    override fun getWrapperClass(): Class<S20PacketEntityProperties> {
        return S20PacketEntityProperties::class.java
    }

    override val humanReadableName: String
        get() = "S20PacketEntityProperties"
    override val id: Int
        get() = 32
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S20PacketEntityProperties protected constructor(original: Any): Packet(original) {

}
