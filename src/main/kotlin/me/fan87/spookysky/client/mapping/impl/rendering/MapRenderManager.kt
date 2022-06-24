package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.entities.Entity

object MapRenderManager : ClassMapping<RenderManager>() {
    override fun getWrapperClass(): Class<RenderManager> {
        return RenderManager::class.java
    }

    override val humanReadableName: String
        get() = "RenderManager"


    val mapRenderEntityStatic = MethodMapping<Boolean, RenderManager>(this, "renderEntityStatic(Entity, float, boolean)")
    val mapRenderEntitySimple = MethodMapping<Boolean, RenderManager>(this, "renderEntitySimple(Entity, float)")
    val mapGetEntityClassRenderObject = MethodMapping<Any, RenderManager>(this, "getEntityClassRenderObject(Class<? extends Entity>)")

    val mapEntityRenderMap = FieldMapping<Map<Class<*>, *>, RenderManager>(this, "entityRenderMap")
}

open class RenderManager protected constructor(original: Any) : WrapperClass(original) {

    fun renderEntityStatic(entity: Entity, partialTick: Float, hideDebugBox: Boolean): Boolean {
        return MapRenderManager.mapRenderEntityStatic.invoke(this, entity.original, partialTick, hideDebugBox)!!
    }

    fun renderEntitySimple(entity: Entity, partialTick: Float): Boolean {
        return renderEntityStatic(entity, partialTick, false)
    }

    fun <T: Entity> getEntityClassRenderObject(entity: T): Render<T> {
        val renderOriginal = MapRenderManager.mapGetEntityClassRenderObject.invoke(this, entity.original.javaClass)
            ?: throw IllegalArgumentException("Could not find render object for entity: ${entity.original}")
        return MappingsManager.getWrapped(renderOriginal)
    }


}
