package me.fan87.spookysky.client.mapping

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.exception.MissingMappingException
import org.objectweb.asm.tree.MethodNode
import kotlin.concurrent.withLock

abstract class Mapping<T: MappedInfo>() {

    abstract val humanReadableName: String

    var mapped: T? = null
        set(value) {
            if (value == null) {
                field = null
                return
            }
            if (field == null) {
                SpookySky.debug("$humanReadableName has been mapped (${value.name})")
            }
            field = value
            SpookySky.INSTANCE.mappingsManager.updateLock.withLock {
                SpookySky.INSTANCE.mappingsManager.condition.signalAll()
            }
        }


    fun isMapped(): Boolean = mapped != null

    override fun toString(): String {
        return javaClass.simpleName + ":$humanReadableName"
    }

    fun assumeMapped(): T {
        if (!isMapped()) {
            throw MissingMappingException("${toString()} has not been mapped yet")
        }
        return mapped!!
    }
}

