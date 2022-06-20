
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS0EPacketSpawnObject: PacketMapping<S0EPacketSpawnObject>() {
    override fun getWrapperClass(): Class<S0EPacketSpawnObject> {
        return S0EPacketSpawnObject::class.java
    }

    override val humanReadableName: String
        get() = "S0EPacketSpawnObject"
    override val id: Int
        get() = 0x0E
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S0EPacketSpawnObject protected constructor(original: Any): Packet(original) {

}
