package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.MapFrameBuffer
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes

class ProcessorMapFrameBufferMembers: Processor("Map FrameBuffer Members") {

    init {
        dependsOn(MapFrameBuffer)
    }

    override fun start() {
        onlyProcess(MapFrameBuffer)
    }

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenThis()
            thenPushInt(-1)
            thenGroup("field") {
                thenOpcodeCheck(Opcodes.PUTFIELD)
            }
        }

        val method = clazz.node.methods.first { it.name == "<init>" }
        val matcher = pattern.matcher(method)
        matcher.next()
        matcher.next()
        matcher.next()
        MapFrameBuffer.mapDepthBuffer.map(matcher.groupAsFieldInsnNode("field"))

        return false
    }

    override fun jobDone(): Boolean {
        return MapFrameBuffer.mapDepthBuffer.isMapped()
    }
}