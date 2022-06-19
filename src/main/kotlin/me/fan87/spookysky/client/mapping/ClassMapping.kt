package me.fan87.spookysky.client.mapping

import me.fan87.spookysky.client.LoadedClass
import org.objectweb.asm.tree.TypeInsnNode

abstract class ClassMapping<T: WrapperClass>: Mapping<MappedClassInfo>() {

    val children = ArrayList<MemberMapping<*>>()

    fun getJavaClass(): Class<*> {
        return Class.forName(assumeMapped().name.replace("/", "."))
    }

    operator fun invoke(vararg args: Any): Any {
        loopConstructors@for (constructor in getJavaClass().constructors) {
            if (constructor.parameterCount != args.size) continue
            for (withIndex in args.withIndex()) {
                if (!constructor.parameterTypes[withIndex.index].isAssignableFrom(withIndex.value.javaClass)) {
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
    fun map(type: TypeInsnNode) {
        mapped = MappedClassInfo(type.desc)
    }

}