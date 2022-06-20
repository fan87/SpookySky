
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS24PacketBlockAction: PacketMapping<S24PacketBlockAction>() {
    override fun getWrapperClass(): Class<S24PacketBlockAction> {
        return S24PacketBlockAction::class.java
    }

    override val humanReadableName: String
        get() = "S24PacketBlockAction"
    override val id: Int
        get() = 36
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S24PacketBlockAction protected constructor(original: Any): Packet(original) {

}
