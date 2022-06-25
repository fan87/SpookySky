package me.fan87.spookysky.client.processors.impl

import me.fan87.regbex.RegbexPattern
import me.fan87.regbex.utils.toInsnList
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.hook.ReachCallback
import me.fan87.spookysky.client.mapping.impl.rendering.MapEntityRenderer
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.addMethodCall
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.LdcInsnNode

class ProcessorHookReach: Processor("Hook Reach") {

    init {
        onlyProcessMapping(MapEntityRenderer)
    }

    var processed = false

    override fun process(clazz: LoadedClass): Boolean {
        val pattern = RegbexPattern {
            thenLdcStringEqual("pick")
        }
        for (method in clazz.node.methods) {
            if (pattern.matcher(method).next()) {
                val out = InsnList()

                for (instruction in method.instructions) {
                    if (instruction is LdcInsnNode && instruction.cst == 3.0) {
                        out.addMethodCall(ReachCallback::getReach)
                    } else {
                        out.add(instruction)
                    }
                }

                method.instructions = out
                processed = true
                return true
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return processed
    }

}