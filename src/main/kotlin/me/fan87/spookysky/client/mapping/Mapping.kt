package me.fan87.spookysky.client.mapping

import me.fan87.spookysky.client.SpookySky
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

    protected fun checkMapped(): T {
        if (!isMapped()) {
            throw IllegalStateException("${toString()} has not been mapped yet")
        }
        return mapped!!
    }
}

