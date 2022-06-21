package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.MapGui
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

class ProcessorMapGuiMembers: Processor("Map Gui Members") {

    init {
        dependsOn(MapGui)
    }

    override fun start() {
        onlyProcess(MapGui)
    }


    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            mapDrawGradientRect(method)
        }
        return false
    }

    fun mapDrawGradientRect(method: MethodNode) {
        val pattern = RegbexPattern {
            thenPushInt(7424)
        }
        if (pattern.matcher(method).next() && method.desc == "(IIIIII)V") {
            MapGui.mapDrawGradient.map(method)
        }
    }

    override fun jobDone(): Boolean {
        return MapGui.mapDrawGradient.isMapped()
    }
}