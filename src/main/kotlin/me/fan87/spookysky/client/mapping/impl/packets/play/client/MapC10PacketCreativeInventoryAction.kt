
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC10PacketCreativeInventoryAction: PacketMapping<C10PacketCreativeInventoryAction>() {
    override fun getWrapperClass(): Class<C10PacketCreativeInventoryAction> {
        return C10PacketCreativeInventoryAction::class.java
    }

    override val humanReadableName: String
        get() = "C10PacketCreativeInventoryAction"
    override val id: Int
        get() = 16
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C10PacketCreativeInventoryAction protected constructor(original: Any): Packet(original) {

}
