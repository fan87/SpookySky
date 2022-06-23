package me.fan87.spookysky.client

import org.objectweb.asm.tree.ClassNode
import java.util.concurrent.locks.ReentrantLock

class LoadedClass(
    val name: String,
    val node: ClassNode
) {
    private var clazz: Class<*>? = null

    val processLock = ReentrantLock()
    fun getJavaClass(): Class<*>? {
        if (clazz == null) {
            try {
                clazz = Class.forName(name.replace("/", "."), false, SpookySky.INSTANCE.clientClassLoader)
                return clazz
            } catch (e: ClassNotFoundException) {
                return null
            }
        }
        return clazz
    }
}