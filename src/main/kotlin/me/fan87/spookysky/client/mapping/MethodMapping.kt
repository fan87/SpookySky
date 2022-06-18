package me.fan87.spookysky.client.mapping

import me.fan87.regbex.utils.MethodArgumentsTypeReader
import me.fan87.spookysky.client.utils.ASMUtils
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import java.lang.reflect.Method

class MethodMapping<ReturnType, OwnerType: WrapperClass>(parent: ClassMapping<OwnerType>, name: String): MemberMapping<MappedMethodInfo>(parent, name) {


    operator fun invoke(instance: OwnerType?, vararg args: Any): ReturnType? {
        return getJavaMethod().invoke(instance?.original, *args.map { if (it is WrapperClass) it.original else it }.toTypedArray()) as ReturnType?
    }

    fun getJavaMethod(): Method {
        checkMapped()
        val methodArgumentsTypeReader = MethodArgumentsTypeReader(checkMapped().desc)
        return parent.getJavaClass().getDeclaredMethod(mapped!!.name, *methodArgumentsTypeReader.arguments.map { ASMUtils.fromDescType(it) }.toTypedArray()).apply { isAccessible = true }
    }

    fun map(methodName: String, desc: String) {
        mapped = MappedMethodInfo(methodName, desc)
    }

    fun map(node: MethodNode) {
        mapped = MappedMethodInfo(node.name, node.desc)
    }

    fun map(node: MethodInsnNode) {
        mapped = MappedMethodInfo(node.name, node.desc)
    }

}