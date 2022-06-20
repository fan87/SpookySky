
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS32PacketConfirmTransaction: PacketMapping<S32PacketConfirmTransaction>() {
    override fun getWrapperClass(): Class<S32PacketConfirmTransaction> {
        return S32PacketConfirmTransaction::class.java
    }

    override val humanReadableName: String
        get() = "S32PacketConfirmTransaction"
    override val id: Int
        get() = 50
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER
}

open class S32PacketConfirmTransaction protected constructor(original: Any): Packet(original) {

}
