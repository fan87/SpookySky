package me.fan87.spookysky.client.mapping.impl.rendering

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.chat.IChatComponent

object MapGui: ClassMapping<Gui>() {
    override fun getWrapperClass(): Class<Gui> {
        return Gui::class.java
    }

    override val humanReadableName: String
        get() = "Gui"

    val mapDrawGradient = MethodMapping<Unit, Gui>(this, "drawGradient(int, int, int, int, int, int)")

}

open class Gui protected constructor(original: Any): WrapperClass(original) {

    companion object {
        operator fun invoke(): Gui {
            return Gui(MapGui())
        }
    }

    fun drawGradient(left: Int, top: Int, right: Int, bottom: Int, startColor: Long, endColor: Long) {
        MapGui.mapDrawGradient.invoke(this, left, top, right, bottom, startColor.toInt(), endColor.toInt())
    }

}