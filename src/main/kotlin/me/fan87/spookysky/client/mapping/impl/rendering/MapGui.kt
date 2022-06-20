package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.chat.IChatComponent

object MapGui: ClassMapping<Gui>() {
    override fun getWrapperClass(): Class<Gui> {
        return Gui::class.java
    }

    override val humanReadableName: String
        get() = "Gui"

}

open class Gui protected constructor(original: Any): WrapperClass(original) {


}