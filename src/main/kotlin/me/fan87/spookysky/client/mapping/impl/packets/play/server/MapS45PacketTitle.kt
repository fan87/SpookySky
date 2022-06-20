
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS45PacketTitle: PacketMapping<S45PacketTitle>() {
    override fun getWrapperClass(): Class<S45PacketTitle> {
        return S45PacketTitle::class.java
    }

    override val humanReadableName: String
        get() = "S45PacketTitle"
    override val id: Int
        get() = 0x45
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S45PacketTitle protected constructor(original: Any): Packet(original) {

}
