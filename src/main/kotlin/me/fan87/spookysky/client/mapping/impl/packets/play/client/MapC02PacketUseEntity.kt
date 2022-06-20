
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC02PacketUseEntity: PacketMapping<C02PacketUseEntity>() {
    override fun getWrapperClass(): Class<C02PacketUseEntity> {
        return C02PacketUseEntity::class.java
    }

    override val humanReadableName: String
        get() = "C02PacketUseEntity"
    override val id: Int
        get() = 2
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C02PacketUseEntity protected constructor(original: Any): Packet(original) {

}
