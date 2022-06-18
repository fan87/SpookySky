package me.fan87.spookysky.loader

import java.net.URL
import java.net.URLClassLoader

class CustomClassLoader(parent: ClassLoader): URLClassLoader(arrayOf(), parent) {

    public override fun addURL(url: URL?) {
        super.addURL(url)
    }

    override fun loadClass(name: String?): Class<*> {
        return super.loadClass(name)
    }

    public override fun findClass(name: String?): Class<*> {
        return super.findClass(name)
    }
}