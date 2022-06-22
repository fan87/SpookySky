package me.fan87.spookysky.client.mapping.impl.entities


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapEntityLivingBase : ClassMapping<EntityLivingBase>() {
    override fun getWrapperClass(): Class<EntityLivingBase> {
        return EntityLivingBase::class.java
    }

    override val humanReadableName: String
        get() = "EntityLivingBase"

    val mapJump = MethodMapping<Unit, EntityLivingBase>(this, "jump()")

}

open class EntityLivingBase protected constructor(original: Any) : Entity(original) {

    fun jump() = MapEntityLivingBase.mapJump.invoke(this)

}
