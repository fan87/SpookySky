package me.fan87.spookysky.client.mapping.impl.entities


import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.item.ItemStack

object MapEntityLivingBase : ClassMapping<EntityLivingBase>() {
    override fun getWrapperClass(): Class<EntityLivingBase> {
        return EntityLivingBase::class.java
    }

    override val humanReadableName: String
        get() = "EntityLivingBase"

    val mapJump = MethodMapping<Unit, EntityLivingBase>(this, "jump()")
    val mapOnLivingUpdate = MethodMapping<Unit, EntityLivingBase>(this, "onLivingUpdate()")
    val mapSwingItem = MethodMapping<Unit, EntityLivingBase>(this, "swingItem()")
    val mapGetHeldItem = MethodMapping<Any, EntityLivingBase>(this, "getHeldItem()")

    val mapMoveStrafing = FieldMapping<Float, EntityLivingBase>(this, "movingStrafing")
    val mapMoveForward = FieldMapping<Float, EntityLivingBase>(this, "movingForward")


}

open class EntityLivingBase protected constructor(original: Any) : Entity(original) {

    fun jump() = MapEntityLivingBase.mapJump.invoke(this)
    fun onLivingUpdate() = MapEntityLivingBase.mapOnLivingUpdate.invoke(this)
    fun swingItem() = MapEntityLivingBase.mapSwingItem.invoke(this)
    fun getHeldItem(): ItemStack = MappingsManager.getWrapped(MapEntityLivingBase.mapGetHeldItem.invoke(this)!!)

    var moveStrafing: Float by MapEntityLivingBase.mapMoveStrafing
    var moveForward: Float by MapEntityLivingBase.mapMoveForward


    fun isMoving(): Boolean {
        return moveForward != 0f || moveStrafing != 0f
    }

}
