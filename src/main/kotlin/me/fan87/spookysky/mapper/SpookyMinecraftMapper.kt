package me.fan87.spookysky.mapper

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.processors.ProcessorsManager
import me.fan87.spookysky.client.utils.ASMUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.util.CheckClassAdapter
import java.io.File
import java.io.PrintWriter
import kotlin.system.exitProcess

class SpookyMinecraftMapper(val classes: List<ClassNode>, val onError: () -> Unit = { exitProcess(-1) }) {

    val loadedClasses = HashMap<String, LoadedClass>()

    init {
        for (classNode in classes) {
            loadedClasses[classNode.name] = LoadedClass(classNode.name, classNode)
        }
    }

    val mappingsManager = MappingsManager()
    val processorsManager = ProcessorsManager(mappingsManager, { loadedClasses }, {
//        val writeClass = ASMUtils.writeClassNoVerify(it.node)
//        val verifier = CheckClassAdapter.verify(ClassReader(writeClass), javaClass.classLoader, false, PrintWriter(System.err, true))
        println("===== REDEFINE REQUEST =====\nClass Name: ${it.name}"
    ) }).also {
        it.onError = onError
    }



    fun isFinished(): Boolean {
        return mappingsManager.isMapped()
    }


    fun dump(output: PrintWriter, ansi: Boolean) {
        MappingResultDumper(mappingsManager, processorsManager).dump(output, ansi)
    }

    fun dumpToConsole() = dump(PrintWriter(System.out, true), true)
    fun dumpToFile(file: File) {
        val output = file.outputStream()
        dump(PrintWriter(output), false)
        output.close()
    }

    fun stop() {
        for (thread in processorsManager.threads) {
            thread.suspend()
        }
    }

    fun close() {
        for (allMapping in mappingsManager.allMappings) {
            allMapping.mapped = null
        }
    }

}