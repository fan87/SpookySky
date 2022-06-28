package me.fan87.spookysky.client.processors

import me.fan87.regbex.RegbexMatcher
import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.client.utils.ASMUtils.insertInstructions
import org.objectweb.asm.tree.InsnList
import java.io.File

abstract class HookingProcessor(humanReadableName: String) : Processor(humanReadableName) {

    abstract fun getPattern(): RegbexPattern
    abstract fun getInsertIndex(matcher: RegbexMatcher): Int
    abstract fun insertOp(out: InsnList)

    open fun enableDebug(): Boolean {
        return false
    }

    var processed = false

    override fun jobDone(): Boolean {
        return processed
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = getPattern().matcher(method)
            if (matcher.next()) {
                method.instructions = method.instructions.insertInstructions(getInsertIndex(matcher), InsnList().apply { insertOp(this) })
                processed = true
                if (enableDebug()) {
                    println("[SpookySky] [Hooking Processor] ${humanReadableName} has requested outputting the file! Saving file to ${File(System.getProperty("java.io.tmpdir") ,"/${humanReadableName}.class").absoluteFile}")
                    File(System.getProperty("java.io.tmpdir") ,"/${humanReadableName}.class").writeBytes(ASMUtils.writeClass(clazz.node))
                }
                return true
            }
        }
        return false
    }
}