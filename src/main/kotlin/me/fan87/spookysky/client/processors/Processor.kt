package me.fan87.spookysky.client.processors

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.Mapping

abstract class Processor(
    val humanReadableName: String
) {

    val dependencies = ArrayList<Mapping<*>>()
    val onlyProcess = ArrayList<String>()

    open fun start() {

    }

    fun onlyProcess(name: String) {
        onlyProcess.add(name)
    }
    fun onlyProcess(mapping: Mapping<*>) {
        onlyProcess.add(mapping.assumeMapped().name)
    }

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
            unsupportedClient("$mapping was asserted to be mapped, but it's not!")
        }
    }

    fun unsupportedClient(reason: String): Nothing {
        throw IllegalStateException("Unsupported Client: $reason")
    }

    fun assertTrue(value: Boolean) {
        if (!value) {
            unsupportedClient("Expected true, but got false")
        }
    }

}