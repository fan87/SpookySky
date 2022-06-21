package me.fan87.spookysky.client.mapping.impl.world


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.entities.Entity

object MapWorld : ClassMapping<World>() {
    override fun getWrapperClass(): Class<World> {
        return World::class.java
    }

    override val humanReadableName: String
        get() = "World"

    val mapLoadedEntityList = FieldMapping<Any, World>(this, "loadedEntityList")
}

open class World protected constructor(original: Any) : WrapperClass(original) {

    /**
     * # WARNING: Cloned List
     */
    val loadedEntityList: List<Entity>
        get() {
            val out = ArrayList<Entity>()
            for (entity in MapWorld.mapLoadedEntityList.getJavaField().get(original) as List<Any>) {
                out.add(MappingsManager.getWrapped(entity))
            }
            return out
        }

}
