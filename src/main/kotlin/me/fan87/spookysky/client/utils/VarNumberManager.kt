package me.fan87.spookysky.client.utils

import me.fan87.spookysky.client.utils.ASMUtils.getParameterTypes
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.VarInsnNode
import java.lang.reflect.Modifier

class VarNumberManager(val instructions: Iterable<AbstractInsnNode>) {

    constructor(method: MethodNode): this(method.instructions) {
        if (!Modifier.isStatic(method.access)) {
            current = current().coerceAtLeast(method.getParameterTypes().size + 1)
        } else {
            current = current().coerceAtLeast(method.getParameterTypes().size)
        }
    }

    private var current = 0

    init {
        for (instruction in instructions) {
            if (instruction is VarInsnNode) {
                current = instruction.`var`.coerceAtLeast(current)
            }
        }
    }

    fun current(): Int {
        return current
    }

    fun seekNext(): Int  {
        return current + 1
    }

    fun allocateVariable(): Int {
        current += 1
        return current
    }

}