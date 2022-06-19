package me.fan87.spookysky.client.processors

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.Mapping
import me.fan87.spookysky.client.utils.ASMUtils
import org.apache.logging.log4j.core.config.plugins.ResolverUtil
import org.objectweb.asm.ClassReader
import org.objectweb.asm.util.CheckClassAdapter
import java.io.PrintWriter
import java.lang.instrument.ClassDefinition
import java.lang.reflect.Modifier
import java.net.URI
import kotlin.concurrent.withLock

class ProcessorsManager(val spookySky: SpookySky) {

    val processors = ArrayList<Processor>()

    init {
        val resolver = ResolverUtil()
        resolver.classLoader = ProcessorsManager::class.java.classLoader
        resolver.findInPackage(object : ResolverUtil.Test {
            override fun matches(type: Class<*>?): Boolean {
                return Processor::class.java.isAssignableFrom(type) && !Modifier.isAbstract(type!!.modifiers)
            }

            override fun matches(resource: URI?): Boolean {
                return true
            }

            override fun doesMatchClass(): Boolean {
                return true
            }

            override fun doesMatchResource(): Boolean {
                return false
            }
        }, ProcessorsManager::class.java.`package`.name)

        for (clazz in resolver.classes) {
            val processor = clazz.newInstance() as Processor
            processors.add(processor)
            SpookySky.debug("Registered Processor: ${processor.humanReadableName}")
            Thread {
                while (!processor.canProcess()) {
                    spookySky.mappingsManager.updateLock.withLock {
                        spookySky.mappingsManager.condition.await()
                    }
                }
                SpookySky.debug("[Processors Manager] Running processor: ${processor.humanReadableName} as all of its dependencies has been solved")
                var firstTime = false
                while (!processor.jobDone()) {
                    try {
                        for (mutableEntry in HashMap(spookySky.classes)) {
                            if (mutableEntry.value.name.startsWith("me/fan87/spookysky")) continue
                            if (processor.process(mutableEntry.value)) {
                                SpookySky.debug("[Processors Manager] Processor has redefined a class: ${mutableEntry.value.node.name}")
                                val writeClass = ASMUtils.writeClass(mutableEntry.value.node)
                                val verifier = CheckClassAdapter.verify(ClassReader(writeClass), javaClass.classLoader, false, PrintWriter(System.err, true))
                                spookySky.instrumentation.redefineClasses(ClassDefinition(mutableEntry.value.getJavaClass(),
                                    writeClass
                                ))
                                SpookySky.debug("[Processors Manager] Successfully redefined ${mutableEntry.value.node.name}")
                            }
                            if (!firstTime) {
                                Thread.sleep(1)
                            }
                            if (processor.jobDone()) {
                                SpookySky.debug("[Processors Manager] Processor: ${processor.humanReadableName} has got its job done")
                                return@Thread
                            }
                        }
                        Thread.sleep(10)
                        firstTime = false
                    } catch (_: ConcurrentModificationException) {}
                }
            }.start()
        }


    }

}