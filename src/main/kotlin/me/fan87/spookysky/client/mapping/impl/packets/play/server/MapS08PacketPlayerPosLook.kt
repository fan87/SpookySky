
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS08PacketPlayerPosLook: PacketMapping<S08PacketPlayerPosLook>() {
    override fun getWrapperClass(): Class<S08PacketPlayerPosLook> {
        return S08PacketPlayerPosLook::class.java
    }

    override val humanReadableName: String
        get() = "S08PacketPlayerPosLook"
    override val id: Int
        get() = 8
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S08PacketPlayerPosLook protected constructor(original: Any): Packet(original) {

}
