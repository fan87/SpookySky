package me.fan87.spookysky.client.mapping.impl.world


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapWorldClient : ClassMapping<WorldClient>() {
    override fun getWrapperClass(): Class<WorldClient> {
        return WorldClient::class.java
    }

    override val humanReadableName: String
        get() = "WorldClient"
}

open class WorldClient protected constructor(original: Any) : World(original) {

}
