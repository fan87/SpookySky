package me.fan87.spookysky.client.mapping.impl.entities


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapPlayerCapabilities : ClassMapping<PlayerCapabilities>() {
    override fun getWrapperClass(): Class<PlayerCapabilities> {
        return PlayerCapabilities::class.java
    }

    override val humanReadableName: String
        get() = "PlayerCapabilities"
}

open class PlayerCapabilities protected constructor(original: Any) : WrapperClass(original) {

}
