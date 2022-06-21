package me.fan87.spookysky.client

import org.objectweb.asm.tree.ClassNode

class LoadedClass(
    val name: String,
    val node: ClassNode
) {
    fun getJavaClass(): Class<*> {
        return Class.forName(name.replace("/", "."), false, javaClass.classLoader)
    }
}