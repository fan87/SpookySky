
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS00PacketKeepAlive: PacketMapping<S00PacketKeepAlive>() {
    override fun getWrapperClass(): Class<S00PacketKeepAlive> {
        return S00PacketKeepAlive::class.java
    }

    override val humanReadableName: String
        get() = "S00PacketKeepAlive"
    override val id: Int
        get() = 0
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S00PacketKeepAlive protected constructor(original: Any): Packet(original) {

}
