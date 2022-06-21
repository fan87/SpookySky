package me.fan87.spookysky.client.mapping.impl.packets


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapPacketBuffer : ClassMapping<PacketBuffer>() {
    override fun getWrapperClass(): Class<PacketBuffer> {
        return PacketBuffer::class.java
    }

    override val humanReadableName: String
        get() = "PacketBuffer"
}

open class PacketBuffer protected constructor(original: Any) : WrapperClass(original) {

}
