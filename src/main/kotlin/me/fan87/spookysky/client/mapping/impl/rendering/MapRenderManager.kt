package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.entities.Entity

object MapRenderManager : ClassMapping<RenderManager>() {
    override fun getWrapperClass(): Class<RenderManager> {
        return RenderManager::class.java
    }

    override val humanReadableName: String
        get() = "RenderManager"


    val mapRenderEntityStatic = MethodMapping<Boolean, RenderManager>(this, "renderEntityStatic(Entity, float, boolean)")
}

open class RenderManager protected constructor(original: Any) : WrapperClass(original) {

    fun renderEntityStatic(entity: Entity, partialTick: Float, hideDebugBox: Boolean): Boolean {
        return MapRenderManager.mapRenderEntityStatic.invoke(this, entity.original, partialTick, hideDebugBox)!!
    }

    fun renderEntitySimple(entity: Entity, partialTick: Float): Boolean {
        return renderEntityStatic(entity, partialTick, false)
    }

}
