package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.entities.Entity

object MapRender : ClassMapping<Render<*>>() {
    override fun getWrapperClass(): Class<Render<*>> {
        return Render::class.java
    }

    override val humanReadableName: String
        get() = "Render"

    val mapRenderShadow = MethodMapping<Unit, Render<*>>(this, "renderShadow(Entity, double, double, double, float, float)")
    val mapRenderLivingLabel = MethodMapping<Unit, Render<*>>(this, "renderLivingLabel(Entity, String, double, double, double, int)")
}

open class Render<T: Entity> protected constructor(original: Any) : WrapperClass(original) {


}
