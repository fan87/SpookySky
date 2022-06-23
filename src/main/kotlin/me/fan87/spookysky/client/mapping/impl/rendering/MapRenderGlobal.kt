package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapRenderGlobal : ClassMapping<RenderGlobal>() {
    override fun getWrapperClass(): Class<RenderGlobal> {
        return RenderGlobal::class.java
    }

    override val humanReadableName: String
        get() = "RenderGlobal"


    val mapRenderEntities = MethodMapping<Unit, RenderGlobal>(this, "renderEntities(Entity, ICamera, float)")
}

open class RenderGlobal protected constructor(original: Any) : WrapperClass(original) {


}
