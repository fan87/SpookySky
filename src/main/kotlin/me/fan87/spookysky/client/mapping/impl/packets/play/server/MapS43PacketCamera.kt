
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS43PacketCamera: PacketMapping<S43PacketCamera>() {
    override fun getWrapperClass(): Class<S43PacketCamera> {
        return S43PacketCamera::class.java
    }

    override val humanReadableName: String
        get() = "S43PacketCamera"
    override val id: Int
        get() = 67
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S43PacketCamera protected constructor(original: Any): Packet(original) {

}
