package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapFrameBuffer : ClassMapping<FrameBuffer>() {
    override fun getWrapperClass(): Class<FrameBuffer> {
        return FrameBuffer::class.java
    }

    override val humanReadableName: String
        get() = "FrameBuffer"


    val mapDepthBuffer = FieldMapping<Int, FrameBuffer>(this, "depthBuffer")
}

open class FrameBuffer protected constructor(original: Any) : WrapperClass(original) {

    var depthBuffer by MapFrameBuffer.mapDepthBuffer

}
