
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC0EPacketClickWindow: PacketMapping<C0EPacketClickWindow>() {
    override fun getWrapperClass(): Class<C0EPacketClickWindow> {
        return C0EPacketClickWindow::class.java
    }

    override val humanReadableName: String
        get() = "C0EPacketClickWindow"
    override val id: Int
        get() = 14
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT
}

open class C0EPacketClickWindow protected constructor(original: Any): Packet(original) {

}
