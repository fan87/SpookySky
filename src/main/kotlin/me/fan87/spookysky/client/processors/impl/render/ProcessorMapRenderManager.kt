package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.MapRenderManager
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.tree.ClassNode

class ProcessorMapRenderManager: Processor("Map RenderManager") {

    val pattern = RegbexPattern {
        thenLdcStringEqual("Rendering entity in world")
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapRenderManager.map(clazz)
                mapMembers(clazz.node)
            }
        }
        return false
    }

    fun mapMembers(clazz: ClassNode) {
        mapRenderEntityStatic(clazz)
    }

    fun mapRenderEntityStatic(clazz: ClassNode) {
        val pattern = RegbexPattern {
            thenLdc(15728880)
        }
        for (method in clazz.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapRenderManager.mapRenderEntityStatic.map(method)
            }
        }
    }

    override fun jobDone(): Boolean {
        return MapRenderManager.isMapped();
    }
}