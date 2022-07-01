
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS29PacketSoundEffect: PacketMapping<S29PacketSoundEffect>() {
    override fun getWrapperClass(): Class<S29PacketSoundEffect> {
        return S29PacketSoundEffect::class.java
    }

    override val humanReadableName: String
        get() = "S29PacketSoundEffect"
    override val id: Int
        get() = 41
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER

    val mapSoundName = FieldMapping<String, S29PacketSoundEffect>(this, "soundName")
    val mapPosX = FieldMapping<Int, S29PacketSoundEffect>(this, "posX")
    val mapPosY = FieldMapping<Int, S29PacketSoundEffect>(this, "posY")
    val mapPosZ = FieldMapping<Int, S29PacketSoundEffect>(this, "posZ")
    val mapSoundVolume = FieldMapping<Float, S29PacketSoundEffect>(this, "soundVolume")
    val mapSoundPitch = FieldMapping<Int, S29PacketSoundEffect>(this, "soundPitch")

    override fun getDataOrder(): List<FieldMapping<*, *>> {
        return listOf(mapSoundName, mapPosX, mapPosY, mapPosZ, mapSoundVolume, mapSoundPitch)
    }
}

open class S29PacketSoundEffect protected constructor(original: Any): Packet(original) {
    val soundName by MapS29PacketSoundEffect.mapSoundName
    val posX by MapS29PacketSoundEffect.mapPosX
    val posY by MapS29PacketSoundEffect.mapPosY
    val posZ by MapS29PacketSoundEffect.mapPosZ
    val soundVolume by MapS29PacketSoundEffect.mapSoundVolume
    val soundPitch by MapS29PacketSoundEffect.mapSoundPitch
}
