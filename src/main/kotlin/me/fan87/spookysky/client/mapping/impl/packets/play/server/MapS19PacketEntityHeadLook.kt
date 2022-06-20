
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS19PacketEntityHeadLook: PacketMapping<S19PacketEntityHeadLook>() {
    override fun getWrapperClass(): Class<S19PacketEntityHeadLook> {
        return S19PacketEntityHeadLook::class.java
    }

    override val humanReadableName: String
        get() = "S19PacketEntityHeadLook"
    override val id: Int
        get() = 25
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S19PacketEntityHeadLook protected constructor(original: Any): Packet(original) {

}
