
package me.fan87.spookysky.client.mapping.impl.packets.play.client

import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapC03PacketPlayer: PacketMapping<C03PacketPlayer>() {
    override fun getWrapperClass(): Class<C03PacketPlayer> {
        return C03PacketPlayer::class.java
    }

    override val humanReadableName: String
        get() = "C03PacketPlayer"
    override val id: Int
        get() = 3
    override val mode: PacketSource
        get() = PacketSource.PLAY_CLIENT

    val mapX = FieldMapping<Double, C03PacketPlayer>(this, "x")
    val mapY = FieldMapping<Double, C03PacketPlayer>(this, "y")
    val mapZ = FieldMapping<Double, C03PacketPlayer>(this, "z")
    val mapYaw = FieldMapping<Float, C03PacketPlayer>(this, "yaw")
    val mapPitch = FieldMapping<Float, C03PacketPlayer>(this, "pitch")
    val mapOnGround = FieldMapping<Boolean, C03PacketPlayer>(this, "onGround")

    override fun getDataOrder(): List<FieldMapping<*, C03PacketPlayer>> {
        return arrayListOf(mapOnGround)
    }
}

open class C03PacketPlayer protected constructor(original: Any): Packet(original) {

    var x by MapC03PacketPlayer.mapOnGround
    var y by MapC03PacketPlayer.mapOnGround
    var z by MapC03PacketPlayer.mapOnGround
    var yaw by MapC03PacketPlayer.mapOnGround
    var pitch by MapC03PacketPlayer.mapOnGround
    var onGround by MapC03PacketPlayer.mapOnGround

}
