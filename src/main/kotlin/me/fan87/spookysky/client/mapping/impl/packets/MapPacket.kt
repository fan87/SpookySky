package me.fan87.spookysky.client.mapping.impl.packets

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapPacket: ClassMapping<Packet>() {
    override fun getWrapperClass(): Class<Packet> {
        return Packet::class.java
    }

    override val humanReadableName: String
        get() = "Packet"
}

open class Packet protected constructor(original: Any) : WrapperClass(original) {

}