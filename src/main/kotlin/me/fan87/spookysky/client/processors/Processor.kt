package me.fan87.spookysky.client.processors

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.Mapping

abstract class Processor(
    val humanReadableName: String
) {

    val dependencies = ArrayList<Mapping<*>>()

    fun dependsOn(mapping: Mapping<*>) {
        dependencies.add(mapping)
    }

    open fun canProcess(): Boolean {
        for (dependency in dependencies) {
            if (!dependency.isMapped()) {
                return false
            }
        }
        return true
    }


    abstract fun process(clazz: LoadedClass): Boolean
    abstract fun jobDone(): Boolean

    fun assertMapped(mapping: Mapping<*>) {
        if (!mapping.isMapped()) {
            throw IllegalStateException("$mapping was asserted to be mapped, but it's not!")
        }
    }

}