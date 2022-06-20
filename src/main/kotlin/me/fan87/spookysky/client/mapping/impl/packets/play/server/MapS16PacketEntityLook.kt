
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS16PacketEntityLook: PacketMapping<S16PacketEntityLook>() {
    override fun getWrapperClass(): Class<S16PacketEntityLook> {
        return S16PacketEntityLook::class.java
    }

    override val humanReadableName: String
        get() = "S16PacketEntityLook"
    override val id: Int
        get() = 0x16
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S16PacketEntityLook protected constructor(original: Any): Packet(original) {

}
