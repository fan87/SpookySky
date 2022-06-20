
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC13PacketPlayerAbilities: PacketMapping<C13PacketPlayerAbilities>() {
    override fun getWrapperClass(): Class<C13PacketPlayerAbilities> {
        return C13PacketPlayerAbilities::class.java
    }

    override val humanReadableName: String
        get() = "C13PacketPlayerAbilities"
    override val id: Int
        get() = 19
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C13PacketPlayerAbilities protected constructor(original: Any): Packet(original) {

}
