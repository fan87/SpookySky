
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS42PacketCombatEvent: PacketMapping<S42PacketCombatEvent>() {
    override fun getWrapperClass(): Class<S42PacketCombatEvent> {
        return S42PacketCombatEvent::class.java
    }

    override val humanReadableName: String
        get() = "S42PacketCombatEvent"
    override val id: Int
        get() = 0x42
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S42PacketCombatEvent protected constructor(original: Any): Packet(original) {

}
