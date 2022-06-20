package me.fan87.spookysky.client.mapping.impl.settings


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapKeyBinding : ClassMapping<KeyBinding>() {
    override fun getWrapperClass(): Class<KeyBinding> {
        return KeyBinding::class.java
    }

    override val humanReadableName: String
        get() = "KeyBinding"
}

open class KeyBinding(original: Any) : WrapperClass(original) {

}
