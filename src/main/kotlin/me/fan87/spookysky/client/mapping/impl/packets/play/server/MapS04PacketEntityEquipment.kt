
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS04PacketEntityEquipment: PacketMapping<S04PacketEntityEquipment>() {
    override fun getWrapperClass(): Class<S04PacketEntityEquipment> {
        return S04PacketEntityEquipment::class.java
    }

    override val humanReadableName: String
        get() = "S04PacketEntityEquipment"
    override val id: Int
        get() = 0x04
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S04PacketEntityEquipment protected constructor(original: Any): Packet(original) {

}
