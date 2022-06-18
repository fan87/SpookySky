package me.fan87.spookysky.client

import com.github.philippheuer.events4j.core.EventManager
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.module.ModulesManager
import me.fan87.spookysky.client.processors.ProcessorsManager
import me.fan87.spookysky.client.utils.ASMUtils
import org.objectweb.asm.tree.ClassNode
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation

class SpookySky(
    val instrumentation: Instrumentation,
    val preLoadedClasses: HashMap<String, ClassNode>,
    val transformer: ClassFileTransformer
) {

    companion object {
        lateinit var INSTANCE: SpookySky


        fun debug(message: Any) {
            println("[SpookySky] $message")
        }
    }
    val classes = HashMap<String, LoadedClass>()

    val mappingsManager: MappingsManager
    val processorsManager: ProcessorsManager
    val modulesManager: ModulesManager

    val eventManager = EventManager()

    init {
        INSTANCE = this
        debug("SpookySky has been injected to class loader: ${javaClass.classLoader}! Initializing...")
        instrumentation.addTransformer { loader, className, classBeingRedefined, protectionDomain, classfileBuffer ->
            val node = ASMUtils.parseClass(classfileBuffer)
            val name = className.replace(".", "/")
            classes[name] = LoadedClass(name, node)
            classfileBuffer
        }
        instrumentation.removeTransformer(transformer)
        for (preLoadedClass in preLoadedClasses) {
            classes[preLoadedClass.key] = LoadedClass(preLoadedClass.key, preLoadedClass.value)
        }

        mappingsManager = MappingsManager(this)
        processorsManager = ProcessorsManager(this)
        modulesManager = ModulesManager(this)

    }



}