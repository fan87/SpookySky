
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC0DPacketCloseWindow: PacketMapping<C0DPacketCloseWindow>() {
    override fun getWrapperClass(): Class<C0DPacketCloseWindow> {
        return C0DPacketCloseWindow::class.java
    }

    override val humanReadableName: String
        get() = "C0DPacketCloseWindow"
    override val id: Int
        get() = 0x0D
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C0DPacketCloseWindow protected constructor(original: Any): Packet(original) {

}
