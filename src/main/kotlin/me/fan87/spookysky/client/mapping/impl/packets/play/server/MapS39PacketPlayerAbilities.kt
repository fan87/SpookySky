
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS39PacketPlayerAbilities: PacketMapping<S39PacketPlayerAbilities>() {
    override fun getWrapperClass(): Class<S39PacketPlayerAbilities> {
        return S39PacketPlayerAbilities::class.java
    }

    override val humanReadableName: String
        get() = "S39PacketPlayerAbilities"
    override val id: Int
        get() = 0x39
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S39PacketPlayerAbilities protected constructor(original: Any): Packet(original) {

}