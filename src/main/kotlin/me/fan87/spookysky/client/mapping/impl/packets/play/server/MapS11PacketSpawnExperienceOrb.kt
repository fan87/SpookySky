
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS11PacketSpawnExperienceOrb: PacketMapping<S11PacketSpawnExperienceOrb>() {
    override fun getWrapperClass(): Class<S11PacketSpawnExperienceOrb> {
        return S11PacketSpawnExperienceOrb::class.java
    }

    override val humanReadableName: String
        get() = "S11PacketSpawnExperienceOrb"
    override val id: Int
        get() = 0x11
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S11PacketSpawnExperienceOrb protected constructor(original: Any): Packet(original) {

}
