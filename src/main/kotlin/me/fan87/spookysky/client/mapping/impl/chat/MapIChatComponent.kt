package me.fan87.spookysky.client.mapping.impl.chat

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapIChatComponent: ClassMapping<IChatComponent>() {
    override val humanReadableName: String
        get() = "IChatComponent"


}

open class IChatComponent(original: Any): WrapperClass(original) {

}