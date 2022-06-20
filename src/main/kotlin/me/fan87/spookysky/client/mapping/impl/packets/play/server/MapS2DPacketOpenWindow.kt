
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS2DPacketOpenWindow: PacketMapping<S2DPacketOpenWindow>() {
    override fun getWrapperClass(): Class<S2DPacketOpenWindow> {
        return S2DPacketOpenWindow::class.java
    }

    override val humanReadableName: String
        get() = "S2DPacketOpenWindow"
    override val id: Int
        get() = 0x2D
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S2DPacketOpenWindow protected constructor(original: Any): Packet(original) {

}
