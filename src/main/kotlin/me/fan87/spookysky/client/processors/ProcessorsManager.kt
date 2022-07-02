package me.fan87.spookysky.client.processors

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.mapper.MappingResultDumper
import org.apache.logging.log4j.core.config.plugins.ResolverUtil
import java.io.PrintWriter
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.net.URI
import kotlin.concurrent.withLock
import kotlin.system.exitProcess

class ProcessorsManager(val mappingsManager: MappingsManager, val classesProvider: () -> Map<String, LoadedClass>, val redefiner: (LoadedClass) -> Unit) {

    val processors = ArrayList<Processor>()
    val threads = ArrayList<Thread>()

    var onError: () -> Unit = {
        exitProcess(-1)
    }

    init {
        val resolver = ResolverUtil()
        resolver.classLoader = javaClass.classLoader
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
        }, javaClass.`package`.name)

        for (clazz in resolver.classes) {
            val processor = clazz.newInstance() as Processor
            processor.processorsManager = this
            processor.mappingsManager = mappingsManager
            processors.add(processor)
            SpookySky.debug("Registered Processor: ${processor.humanReadableName}")
            threads.add(Thread {
                while (!processor.canProcess()) {
                    mappingsManager.updateLock.withLock {
                        mappingsManager.condition.await()
                    }
                }
                SpookySky.debug("[Processors Manager] Running processor: ${processor.humanReadableName} as all of its dependencies has been solved")
                var firstTime = false
                processor.start()
                for (onlyProcessMapping in processor.onlyProcessMappings) {
                    processor.onlyProcess(onlyProcessMapping)
                }
                while (!processor.jobDone()) {
                    try {
                        if (processor.onlyProcess.isEmpty()) {
                            for (mutableEntry in HashMap(classesProvider())) {
                                mutableEntry.value.processLock.lock()
                                try {
                                    if (mutableEntry.value.name.startsWith("me/fan87/spookysky")) continue
                                    if (processor.process(mutableEntry.value)) {
                                        SpookySky.debug("[Processors Manager] Processor \"${processor.humanReadableName}\" has redefined a class: ${mutableEntry.value.node.name}")
                                        redefiner(mutableEntry.value)
                                        SpookySky.debug("[Processors Manager] Successfully redefined ${mutableEntry.value.node.name}")
                                    }
                                    if (!firstTime) {
                                        Thread.sleep(0, 10)
                                    }
                                    if (processor.jobDone()) {
                                        SpookySky.debug("[Processors Manager] Processor: ${processor.humanReadableName} has got its job done")
                                        return@Thread
                                    }
                                } catch (e: Throwable) {
                                    e.printStackTrace()
                                    System.err.println("(Invocation Target Exception's Cause:)")
                                    if (e is InvocationTargetException) {
                                        e.targetException.printStackTrace()
                                    }
                                    onError()
                                } finally {
                                    mutableEntry.value.processLock.unlock()
                                }
                            }
                        } else {
                            for (onlyProcess in processor.onlyProcess) {
                                val node = classesProvider()[onlyProcess]
                                if (node == null) {
                                    continue
                                }
                                node.processLock.lock()
                                try {
                                    if (processor.process(node)) {
                                        SpookySky.debug("[Processors Manager] Processor \"${processor.humanReadableName}\" has redefined a class: ${node.node.name}")
                                        redefiner(node)
                                        SpookySky.debug("[Processors Manager] Successfully redefined ${node.node.name}")
                                    }
                                    if (!firstTime) {
                                        Thread.sleep(0, 10)
                                    }
                                    if (processor.jobDone()) {
                                        SpookySky.debug("[Processors Manager] Processor: ${processor.humanReadableName} has got its job done")
                                        return@Thread
                                    }
                                } catch (e: Throwable) {
                                    e.printStackTrace()
                                    onError()
                                } finally {
                                    node.processLock.unlock()
                                }
                            }
                        }

                        Thread.sleep(10)
                        firstTime = false
                    } catch (_: ConcurrentModificationException) {}
                }
            }.also { it.start() })
        }


    }

}