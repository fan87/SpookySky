package me.fan87.spookysky.client

import org.objectweb.asm.tree.ClassNode
import java.util.concurrent.locks.ReentrantLock

class LoadedClass(
    val name: String,
    val node: ClassNode
) {
    val processLock = ReentrantLock()
    fun getJavaClass(): Class<*> {
        return Class.forName(name.replace("/", "."), false, javaClass.classLoader)
    }
}