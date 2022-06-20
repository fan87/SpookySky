
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC11PacketEnchantItem: PacketMapping<C11PacketEnchantItem>() {
    override fun getWrapperClass(): Class<C11PacketEnchantItem> {
        return C11PacketEnchantItem::class.java
    }

    override val humanReadableName: String
        get() = "C11PacketEnchantItem"
    override val id: Int
        get() = 17
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C11PacketEnchantItem protected constructor(original: Any): Packet(original) {

}
