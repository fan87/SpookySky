package me.fan87.spookysky.client.processors

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.Mapping
import me.fan87.spookysky.client.utils.ASMUtils
import org.apache.logging.log4j.core.config.plugins.ResolverUtil
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
                            if (processor.process(mutableEntry.value)) {
                                spookySky.instrumentation.redefineClasses(ClassDefinition(mutableEntry.value.getJavaClass(), ASMUtils.writeClass(mutableEntry.value.node)))
                            }
                            if (!firstTime) {
                                Thread.sleep(1)
                            }
                            if (processor.jobDone()) {
                                SpookySky.debug("[Processors Manager] Processor: ${processor.humanReadableName} has got its job done")
                                return@Thread
                            }
                        }
                        Thread.sleep(100)
                        firstTime = false
                    } catch (_: ConcurrentModificationException) {}
                }
            }.start()
        }


    }

}