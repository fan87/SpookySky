package me.fan87.spookysky.client.mapping.impl.entities

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.Minecraft
import java.util.UUID

object MapEntity: ClassMapping<Entity>() {
    override val humanReadableName: String
        get() = "Entity"

    val mapMotionX = FieldMapping<Double, Entity>(this, "motionX")
    val mapMotionY = FieldMapping<Double, Entity>(this, "motionY")
    val mapMotionZ = FieldMapping<Double, Entity>(this, "motionZ")
    val mapPosX = FieldMapping<Double, Entity>(this, "posX")
    val mapPosY = FieldMapping<Double, Entity>(this, "posY")
    val mapPosZ = FieldMapping<Double, Entity>(this, "posZ")
//    val mapLastTickPosX = FieldMapping<Double, Entity>(this, "lastTickPosX")
//    val mapLastTickPosY = FieldMapping<Double, Entity>(this, "lastTickPosY")
//    val mapLastTickPosZ = FieldMapping<Double, Entity>(this, "lastTickPosZ")
    val mapRotationYaw = FieldMapping<Float, Entity>(this, "rotationYaw")
    val mapRotationPitch = FieldMapping<Float, Entity>(this, "rotationPitch")
    val mapOnGround = FieldMapping<Boolean, Entity>(this, "onGround")
//    val mapIsCollidedHorizontally = FieldMapping<Boolean, Entity>(this, "isCollidedHorizontally")
//    val mapIsCollidedVertically = FieldMapping<Boolean, Entity>(this, "isCollidedVertically")
//    val mapIsCollided = FieldMapping<Boolean, Entity>(this, "isCollided")
//    val mapWidth = FieldMapping<Float, Entity>(this, "width")
    val mapHeight = FieldMapping<Float, Entity>(this, "height")
    val mapFallDistance = FieldMapping<Float, Entity>(this, "fallDistance")

    val mapGetUniqueID = MethodMapping<UUID, Entity>(this, "getUniqueID")
    val mapGetEyeHeight = MethodMapping<Float, Entity>(this, "getEyeHeight")

}

open class Entity(original: Any): WrapperClass(original) {

    val motionX by MapEntity.mapMotionX
    val motionY by MapEntity.mapMotionY
    val motionZ by MapEntity.mapMotionZ
    val posX by MapEntity.mapPosX
    val posY by MapEntity.mapPosY
    val posZ by MapEntity.mapPosZ
//    val lastTickPosX by MapEntity.mapLastTickPosX
//    val lastTickPosY by MapEntity.mapLastTickPosY
//    val lastTickPosZ by MapEntity.mapLastTickPosZ
    val rotationYaw by MapEntity.mapRotationYaw
    val rotationPitch by MapEntity.mapRotationPitch
    val onGround by MapEntity.mapOnGround
//    val isCollidedHorizontally by MapEntity.mapIsCollidedHorizontally
//    val isCollidedVertically by MapEntity.mapIsCollidedVertically
//    val isCollided by MapEntity.mapIsCollided
//    val width by MapEntity.mapWidth
    val height by MapEntity.mapHeight
    val fallDistance by MapEntity.mapFallDistance

    fun getUniqueID(): UUID? = MapEntity.mapGetUniqueID.invoke(this)
    fun getEyeHeight(): Float? = MapEntity.mapGetEyeHeight.invoke(this)

}