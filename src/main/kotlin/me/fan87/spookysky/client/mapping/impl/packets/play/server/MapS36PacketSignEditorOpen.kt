
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS36PacketSignEditorOpen: PacketMapping<S36PacketSignEditorOpen>() {
    override fun getWrapperClass(): Class<S36PacketSignEditorOpen> {
        return S36PacketSignEditorOpen::class.java
    }

    override val humanReadableName: String
        get() = "S36PacketSignEditorOpen"
    override val id: Int
        get() = 54
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S36PacketSignEditorOpen protected constructor(original: Any): Packet(original) {

}
