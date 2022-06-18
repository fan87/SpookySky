package me.fan87.spookysky.client.utils

import me.fan87.regbex.PrimitiveType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

object ASMUtils {

    fun parseClass(data: ByteArray): ClassNode {
        val reader = ClassReader(data)
        val classNode = ClassNode()
        reader.accept(classNode, ClassReader.EXPAND_FRAMES)
        return classNode
    }


    fun writeClass(node: ClassNode): ByteArray {
        val reader = ClassWriter(ClassWriter.COMPUTE_FRAMES)
        node.accept(reader)
        return reader.toByteArray()
    }

    fun getDescType(clazz: Class<*>): String {
        if (clazz.isArray) {
            return "[${getDescType(clazz.componentType)}"
        }
        for (value in PrimitiveType.values()) {
            if (value.primitiveType == clazz) {
                return value.jvmName
            }
        }
        return "L" + clazz.name.replace(".", "/") + ";"
    }

    inline fun <reified T> getDescType(): String {
        return getDescType(T::class.java)
    }

    fun fromDescType(name: String): Class<*> {
        for (value in PrimitiveType.values()) {
            if (value.jvmName == name) {
                return value.primitiveType
            }
        }
        if (name.startsWith("L") && name.endsWith(";")) {
            return Class.forName(name.replace("/", ".").let { it.substring(1, it.length - 1) })
        }
        return Class.forName(name)
    }

    fun generateMethodDesc(method: Method): String {
        return generateMethodDesc(
            getDescType(method.returnType),
            *method.parameterTypes.map { getDescType(it) }.toTypedArray()
        )
    }

    fun generateMethodDesc(returnType: String, vararg argumentsTypes: String): String {
        return "(${argumentsTypes.joinToString("")})$returnType";
    }

    fun getMethodCall(method: Method): MethodInsnNode {
        if (Modifier.isStatic(method.modifiers)) {
            if (method.declaringClass.isInterface) {
                return MethodInsnNode(Opcodes.INVOKESTATIC, method.declaringClass.name.replace(".", "/"), method.name, generateMethodDesc(method), true)
            } else {
                return MethodInsnNode(Opcodes.INVOKESTATIC, method.declaringClass.name.replace(".", "/"), method.name, generateMethodDesc(method))
            }
        } else {
            if (method.declaringClass.isInterface) {
                return MethodInsnNode(Opcodes.INVOKEVIRTUAL, method.declaringClass.name.replace(".", "/"), method.name, generateMethodDesc(method), true)
            } else {
                return MethodInsnNode(Opcodes.INVOKEVIRTUAL, method.declaringClass.name.replace(".", "/"), method.name, generateMethodDesc(method))
            }
        }
    }
    fun getMethodCall(method: KFunction<*>): MethodInsnNode {
        val method1 = method.javaMethod ?: throw IllegalArgumentException("Could not get the java method")
        return getMethodCall(method1)
    }

}