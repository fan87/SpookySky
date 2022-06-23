package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.entities.Entity

object MapModelBase : ClassMapping<ModelBase>() {
    override fun getWrapperClass(): Class<ModelBase> {
        return ModelBase::class.java
    }

    override val humanReadableName: String
        get() = "ModelBase"

    val mapRender = MethodMapping<Unit, ModelBase>(this, "render(Entity, float, float, float, float, float)")

}

open class ModelBase protected constructor(original: Any) : WrapperClass(original) {

    fun render(entity: Entity, renderLimbSwing: Float, renderLimbSwingAmount: Float, renderAgeInTicks: Float, renderHeadYaw: Float, renderHeadPitch: Float, renderScaleFactor: Float) {
        MapModelBase.mapRender.invoke(this, entity.original, renderLimbSwing, renderLimbSwingAmount, renderAgeInTicks, renderHeadYaw, renderHeadPitch, renderScaleFactor)
    }

}
