package me.fan87.spookysky.client.mapping.impl.entities


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrappedFieldType

object MapEntityPlayer : ClassMapping<EntityPlayer>() {
    override fun getWrapperClass(): Class<EntityPlayer> {
        return EntityPlayer::class.java
    }

    override val humanReadableName: String
        get() = "EntityPlayer"


    val mapCapabilities = FieldMapping<Any, EntityPlayer>(this, "capabilities")

}

open class EntityPlayer protected constructor(original: Any) : EntityLivingBase(original) {

    val capabilities by WrappedFieldType(MapEntityPlayer.mapCapabilities, PlayerCapabilities::class.java)

}
