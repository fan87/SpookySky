
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS38PacketPlayerListItem: PacketMapping<S38PacketPlayerListItem>() {
    override fun getWrapperClass(): Class<S38PacketPlayerListItem> {
        return S38PacketPlayerListItem::class.java
    }

    override val humanReadableName: String
        get() = "S38PacketPlayerListItem"
    override val id: Int
        get() = 56
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S38PacketPlayerListItem protected constructor(original: Any): Packet(original) {

}
