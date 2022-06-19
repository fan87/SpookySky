package me.fan87.spookysky.client.module

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.processors.ProcessorsManager
import org.apache.logging.log4j.core.config.plugins.ResolverUtil
import java.lang.reflect.Modifier
import java.net.URI

class ModulesManager(val spookySky: SpookySky) {

    val modules = ArrayList<Module>()

    init {
        val resolver = ResolverUtil()
        resolver.classLoader = javaClass.classLoader
        resolver.findInPackage(object : ResolverUtil.Test {
            override fun matches(type: Class<*>?): Boolean {
                return Module::class.java.isAssignableFrom(type) && !Modifier.isAbstract(type!!.modifiers)
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
            val module = clazz.newInstance() as Module
            SpookySky.debug("[Modules Manager] Registered Module: ${module.name}")
            modules.add(module)
        }
    }

    inline fun <reified T: Module> getModule(): T {
        return modules.first { it.javaClass == T::class.java } as T
    }


}