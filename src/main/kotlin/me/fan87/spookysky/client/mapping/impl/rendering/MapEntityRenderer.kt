package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapEntityRenderer : ClassMapping<EntityRenderer>() {
    override fun getWrapperClass(): Class<EntityRenderer> {
        return EntityRenderer::class.java
    }

    override val humanReadableName: String
        get() = "EntityRenderer"

    val mapRenderWorldPass = MethodMapping<Unit, EntityRenderer>(this, "renderWorldPass(int, float, long)")
    val mapOrientCamera = MethodMapping<Unit, EntityRenderer>(this, "orientCamera(float)")
}

open class EntityRenderer protected constructor(original: Any) : WrapperClass(original) {

    fun orientCamera(partialTicks: Float) = MapEntityRenderer.mapOrientCamera.invoke(this, partialTicks)

}
