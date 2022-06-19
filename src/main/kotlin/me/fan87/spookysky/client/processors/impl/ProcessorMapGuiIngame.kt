package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.GuiIngame
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiIngame
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapGuiIngame: Processor("Map GuiIngame") {
    val pattern = RegbexPattern {
        thenLdcStringEqual("titleAndSubtitle")
    }
    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapGuiIngame.map(clazz.name)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapGuiIngame.isMapped()
    }
}