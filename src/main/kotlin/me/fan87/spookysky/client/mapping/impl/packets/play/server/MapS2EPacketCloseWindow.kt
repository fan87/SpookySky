
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS2EPacketCloseWindow: PacketMapping<S2EPacketCloseWindow>() {
    override fun getWrapperClass(): Class<S2EPacketCloseWindow> {
        return S2EPacketCloseWindow::class.java
    }

    override val humanReadableName: String
        get() = "S2EPacketCloseWindow"
    override val id: Int
        get() = 0x2E
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S2EPacketCloseWindow protected constructor(original: Any): Packet(original) {

}
