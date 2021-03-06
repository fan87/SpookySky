package me.fan87.spookysky.client.mapping

import me.fan87.regbex.PrimitiveType
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.utils.ASMUtils.getJvmTypeName
import org.objectweb.asm.tree.TypeInsnNode

abstract class ClassMapping<T: WrapperClass>: Mapping<MappedClassInfo>() {

    val children = ArrayList<MemberMapping<*>>()

    private var cachedJavaClass: Class<*>? = null

    fun getJavaClass(): Class<*> {
        if (cachedJavaClass == null) {
            cachedJavaClass = Class.forName(assumeMapped().name.replace("/", "."), false, SpookySky.INSTANCE.clientClassLoader)
        }
        return cachedJavaClass!!
    }

    abstract fun getWrapperClass(): Class<T>

    operator fun invoke(vararg args: Any): Any {
        loopConstructors@for (constructor in getJavaClass().constructors) {
            if (constructor.parameterCount != args.size) continue
            for (withIndex in args.withIndex()) {
                var matches =
                    constructor.parameterTypes[withIndex.index].isAssignableFrom(withIndex.value.javaClass)
                if (!matches) {
                    for (value in PrimitiveType.values()) {
                        if (constructor.parameterTypes[withIndex.index] == value.primitiveType && withIndex.value.javaClass == value.objectType) {
                            matches = true
                            break
                        }
                    }
                }
                if (!matches) {
                    continue@loopConstructors
                }
            }
            return constructor.newInstance(*args)
        }
        throw NoSuchMethodException("Could not find a constructor with those parameters")
    }


    fun map(className: String) {
        mapped = MappedClassInfo(className)
    }
    fun map(loadedClass: LoadedClass) {
        mapped = MappedClassInfo(loadedClass.node.name)
    }
    fun map(loadedClass: Class<*>) {
        mapped = MappedClassInfo(loadedClass.getJvmTypeName())
    }
    fun map(type: TypeInsnNode) {
        mapped = MappedClassInfo(type.desc)
    }

    fun isInstance(any: Any?): Boolean {
        if (any == null) {
            return false
        }
        return getJavaClass().isAssignableFrom(any.javaClass)
    }

}