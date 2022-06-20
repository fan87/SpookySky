
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS0DPacketCollectItem: PacketMapping<S0DPacketCollectItem>() {
    override fun getWrapperClass(): Class<S0DPacketCollectItem> {
        return S0DPacketCollectItem::class.java
    }

    override val humanReadableName: String
        get() = "S0DPacketCollectItem"
    override val id: Int
        get() = 13
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S0DPacketCollectItem protected constructor(original: Any): Packet(original) {

}
