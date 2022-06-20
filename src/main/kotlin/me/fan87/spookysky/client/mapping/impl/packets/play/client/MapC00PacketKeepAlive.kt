
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC00PacketKeepAlive: PacketMapping<C00PacketKeepAlive>() {
    override fun getWrapperClass(): Class<C00PacketKeepAlive> {
        return C00PacketKeepAlive::class.java
    }

    override val humanReadableName: String
        get() = "C00PacketKeepAlive"
    override val id: Int
        get() = 0
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C00PacketKeepAlive protected constructor(original: Any): Packet(original) {

}
