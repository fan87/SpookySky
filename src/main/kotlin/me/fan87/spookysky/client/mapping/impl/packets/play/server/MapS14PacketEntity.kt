
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS14PacketEntity: PacketMapping<S14PacketEntity>() {
    override fun getWrapperClass(): Class<S14PacketEntity> {
        return S14PacketEntity::class.java
    }

    override val humanReadableName: String
        get() = "S14PacketEntity"
    override val id: Int
        get() = 20
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S14PacketEntity protected constructor(original: Any): Packet(original) {

}
