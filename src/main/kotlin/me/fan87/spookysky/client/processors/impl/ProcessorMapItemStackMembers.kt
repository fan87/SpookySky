package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.item.MapItem
import me.fan87.spookysky.client.mapping.impl.item.MapItemStack
import me.fan87.spookysky.client.processors.Processor

class ProcessorMapItemStackMembers: Processor("Map ItemStack Members") {

    init {
        dependsOn(MapItem)
        onlyProcessMapping(MapItemStack)
    }

    val pattern = RegbexPattern {
        thenPushInt(8)
        thenLazyAnyAmountOf {
            thenAny()
        }
        thenLdcStringEqual("Name")
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (field in clazz.node.fields) {
            if (field.desc == MapItem.assumeMapped().getDescName()) {
                MapItemStack.mapItem.map(field)
            }
        }
        for (method in clazz.node.methods) {
            if (method.desc == "()Ljava/lang/String;" && pattern.matcher(method).next()) {
                MapItemStack.mapGetDisplayName.map(method)
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapItemStack.mapItem.isMapped()
    }
}