
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS35PacketUpdateTileEntity: PacketMapping<S35PacketUpdateTileEntity>() {
    override fun getWrapperClass(): Class<S35PacketUpdateTileEntity> {
        return S35PacketUpdateTileEntity::class.java
    }

    override val humanReadableName: String
        get() = "S35PacketUpdateTileEntity"
    override val id: Int
        get() = 53
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S35PacketUpdateTileEntity protected constructor(original: Any): Packet(original) {

}
