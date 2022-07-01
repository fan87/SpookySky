package me.fan87.spookysky.client.processors.impl

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.item.MapItem
import me.fan87.spookysky.client.mapping.impl.item.MapItemStack
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapItemStackMembers: Processor("Map ItemStack Members") {

    init {
        dependsOn(MapItem)
        onlyProcessMapping(MapItemStack)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (field in clazz.node.fields) {
            if (field.desc == MapItem.assumeMapped().getDescName()) {
                MapItemStack.mapItem.map(field)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapItemStack.mapItem.isMapped()
    }
}