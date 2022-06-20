
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS2CPacketSpawnGlobalEntity: PacketMapping<S2CPacketSpawnGlobalEntity>() {
    override fun getWrapperClass(): Class<S2CPacketSpawnGlobalEntity> {
        return S2CPacketSpawnGlobalEntity::class.java
    }

    override val humanReadableName: String
        get() = "S2CPacketSpawnGlobalEntity"
    override val id: Int
        get() = 44
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S2CPacketSpawnGlobalEntity protected constructor(original: Any): Packet(original) {

}
