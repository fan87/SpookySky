
package me.fan87.spookysky.client.mapping.impl.packets.play.server

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.packets.Packet
import me.fan87.spookysky.client.mapping.impl.packets.PacketMapping
import me.fan87.spookysky.client.mapping.impl.packets.PacketSource  

object MapS12PacketEntityVelocity: PacketMapping<S12PacketEntityVelocity>() {
    override fun getWrapperClass(): Class<S12PacketEntityVelocity> {
        return S12PacketEntityVelocity::class.java
    }

    override val humanReadableName: String
        get() = "S12PacketEntityVelocity"
    override val id: Int
        get() = 18
    override val mode: PacketSource
        get() = PacketSource.PLAY_SERVER

    val mapEntityId = FieldMapping<Int, S12PacketEntityVelocity>(this, "entityId")
    val mapMotionX = FieldMapping<Short, S12PacketEntityVelocity>(this, "motionZ")
    val mapMotionY = FieldMapping<Short, S12PacketEntityVelocity>(this, "motionZ")
    val mapMotionZ = FieldMapping<Short, S12PacketEntityVelocity>(this, "motionZ")

    override fun getDataOrder(): List<FieldMapping<*, *>> {
        return arrayListOf(mapEntityId, mapMotionX, mapMotionY, mapMotionZ)
    }
}

open class S12PacketEntityVelocity protected constructor(original: Any): Packet(original) {

    var entityId by MapS12PacketEntityVelocity.mapEntityId

    var motionX by MapS12PacketEntityVelocity.mapMotionX
    var motionY by MapS12PacketEntityVelocity.mapMotionY
    var motionZ by MapS12PacketEntityVelocity.mapMotionZ

}
