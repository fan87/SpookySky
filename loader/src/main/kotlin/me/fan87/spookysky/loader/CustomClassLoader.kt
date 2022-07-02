package me.fan87.spookysky.loader

import java.net.URL
import java.net.URLClassLoader
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CustomClassLoader(parent: ClassLoader): URLClassLoader(arrayOf(), parent) {

    val loadLock = ReentrantLock()
    val lock = ReentrantLock()

    public override fun addURL(url: URL?) {
        super.addURL(url)
    }

    val classes = HashMap<String, Class<*>?>()
    val loadingClasses = ArrayList<String>()

    override fun loadClass(name: String): Class<*> {
        val value = classes[name]
        if (value != null) {
            return value
        }
        if (loadingClasses.contains(name)) {
            throw ClassNotFoundException(name)
        }
        loadingClasses.add(name)
        val clazz = super.loadClass(name)
        classes[name] = clazz
        return clazz

    }

    public override fun findClass(name: String?): Class<*> {
        lock.withLock {
            return super.findClass(name)
        }
    }
}