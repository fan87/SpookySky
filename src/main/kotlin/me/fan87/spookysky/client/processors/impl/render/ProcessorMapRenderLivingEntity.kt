package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.entities.MapEntity
import me.fan87.spookysky.client.mapping.impl.rendering.MapRender
import me.fan87.spookysky.client.mapping.impl.rendering.MapRenderLivingEntity
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.Opcodes

class ProcessorMapRenderLivingEntity: Processor("Map RenderLivingEntity") {

    init {
        dependsOn(MapRender)
        dependsOn(MapEntity)
    }

    val pattern = RegbexPattern {
        thenLdc(-0.02666667F)
        thenLazyAmountOf(0..30) {
            thenAny()
        }
        thenLdc(-0.02666667F)
        thenLazyAmountOf(0..30) {
            thenAny()
        }
        thenLdc(9.374999F)
    }

    override fun process(clazz: LoadedClass): Boolean {
        if (clazz.node.superName != MapRender.assumeMapped().name) return false
        for (renderName in clazz.node.methods) {
            if (pattern.matcher(renderName).next()) {
                MapRenderLivingEntity.map(clazz)
                return false
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapRenderLivingEntity.isMapped()
    }


}