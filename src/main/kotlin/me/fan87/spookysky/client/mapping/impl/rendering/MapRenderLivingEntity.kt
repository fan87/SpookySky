package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrappedFieldType
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase

object MapRenderLivingEntity : ClassMapping<RenderLivingEntity<*>>() {
    override fun getWrapperClass(): Class<RenderLivingEntity<*>> {
        return RenderLivingEntity::class.java
    }

    override val humanReadableName: String
        get() = "RenderLivingEntity"

    val mapMainModel = FieldMapping<Any, RenderLivingEntity<*>>(this, "mainModel")

}

open class RenderLivingEntity<T: EntityLivingBase> protected constructor(original: Any) : Render<T>(original) {

    val mainModel by WrappedFieldType(MapRenderLivingEntity.mapMainModel, ModelBase::class.java)

}
