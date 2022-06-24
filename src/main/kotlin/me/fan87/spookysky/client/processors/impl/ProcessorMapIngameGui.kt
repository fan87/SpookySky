package me.fan87.spookysky.client.processors.impl

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.rendering.MapGuiIngame
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapIngameGui: Processor("Map ingameGui") {

    init {
        dependsOn(MapMinecraft)
        dependsOn(MapGuiIngame)
    }

    override fun start() {
        onlyProcess(MapMinecraft)
    }

    override fun process(clazz: LoadedClass): Boolean {
        MapMinecraft.mapIngameGui.map(clazz.node.fields.first { it.desc == MapGuiIngame.assumeMapped().getDescName() })
        return false
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.mapIngameGui.isMapped()
    }

}