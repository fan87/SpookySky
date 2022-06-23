package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapFramebuffer : ClassMapping<Framebuffer>() {
    override fun getWrapperClass(): Class<Framebuffer> {
        return Framebuffer::class.java
    }

    override val humanReadableName: String
        get() = "Framebuffer"


    val mapDepthBuffer = FieldMapping<Int, Framebuffer>(this, "depthBuffer")
}

open class Framebuffer protected constructor(original: Any) : WrapperClass(original) {

    var depthBuffer by MapFramebuffer.mapDepthBuffer

}
