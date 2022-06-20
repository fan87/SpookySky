package me.fan87.spookysky.client.mapping

import me.fan87.spookysky.client.SpookySky
import org.apache.logging.log4j.core.config.plugins.ResolverUtil
import java.lang.reflect.Modifier
import java.net.URI
import java.util.concurrent.locks.ReentrantLock

class MappingsManager(val spookySky: SpookySky) {

    companion object {
        fun <T> getWrapped(original: Any): T {
            var highest: Class<T>? = null
            for (mapping in SpookySky.INSTANCE.mappingsManager.mappings) {
                val wrapperClass = mapping.getWrapperClass()
                if (mapping.isInstance(original)) {
                    if (highest == null) {
                        highest = wrapperClass as Class<T>
                    } else {
                        if (highest.isAssignableFrom(wrapperClass) && !wrapperClass.isAssignableFrom(highest)) {
                            highest = wrapperClass as Class<T>
                        }
                    }
                }
            }
            if (highest == null) {
                throw IllegalArgumentException("Un-supported class: ${original.javaClass.name}")
            }
            for (declaredConstructor in highest.declaredConstructors) {
                if (declaredConstructor.parameterCount == 1 && declaredConstructor.parameterTypes[0] == Any::class.java) {
                    declaredConstructor.isAccessible = true
                    return declaredConstructor.newInstance(original) as T
                }
            }
            throw IllegalStateException("No available constructor for wrapper: ${highest.name}")
        }
    }

    val updateLock = ReentrantLock()
    val condition = updateLock.newCondition()

    val mappings = ArrayList<ClassMapping<*>>()

    init {
        val resolver = ResolverUtil()
        resolver.classLoader = javaClass.classLoader
        resolver.findInPackage(object : ResolverUtil.Test {
            override fun matches(type: Class<*>?): Boolean {
                return ClassMapping::class.java.isAssignableFrom(type) && !MemberMapping::class.java.isAssignableFrom(type) && !Modifier.isAbstract(type!!.modifiers)
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
            val declaredField = clazz.getDeclaredField("INSTANCE")
            val mapping = declaredField.get(null) as ClassMapping<*>
            mappings.add(mapping)
        }
    }

}