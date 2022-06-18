package me.fan87.spookysky.client.module

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.processors.ProcessorsManager
import org.apache.logging.log4j.core.config.plugins.ResolverUtil
import java.lang.reflect.Modifier
import java.net.URI

class ModulesManager(val spookySky: SpookySky) {

    init {
        val resolver = ResolverUtil()
        resolver.classLoader = ProcessorsManager::class.java.classLoader
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
        }, ProcessorsManager::class.java.`package`.name)

        for (clazz in resolver.classes) {
            val module = clazz.newInstance() as Module
            SpookySky.debug("[Modules Manager] Registered Module: ${module.name}")
        }
    }


}