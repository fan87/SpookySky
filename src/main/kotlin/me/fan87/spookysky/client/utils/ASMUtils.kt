package me.fan87.spookysky.client.utils

import com.github.philippheuer.events4j.core.EventManager
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import me.fan87.regbex.PrimitiveType
import me.fan87.regbex.utils.MethodArgumentsTypeReader
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.ClientTickEvent
import me.fan87.spookysky.client.utils.ASMUtils.getDescName
import me.fan87.spookysky.client.utils.ASMUtils.getJvmTypeName
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.TypeInsnNode
import org.objectweb.asm.tree.VarInsnNode
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaMethod

object ASMUtils {

    fun parseClass(data: ByteArray): ClassNode {
        val reader = ClassReader(data)
        val classNode = ClassNode()
        reader.accept(classNode, ClassReader.EXPAND_FRAMES)
        return classNode
    }


    fun writeClass(node: ClassNode): ByteArray {
        val writer = PatchedClassWriter(ClassWriter.COMPUTE_FRAMES)
        node.accept(writer)
        return writer.toByteArray()
    }

    fun classToDescType(clazz: Class<*>): String {
        if (clazz.isArray) {
            return "[${classToDescType(clazz.componentType)}"
        }
        for (value in PrimitiveType.values()) {
            if (value.primitiveType == clazz) {
                return value.jvmName
            }
        }
        return "L" + clazz.name.replace(".", "/") + ";"
    }

    inline fun <reified T> classToDescType(): String {
        return classToDescType(T::class.java)
    }

    fun descTypeToClass(name: String): Class<*> {
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

    fun descTypeToJvmType(descType: String): String {
        if (!descType.startsWith("L") || !descType.endsWith(";")) {
            throw IllegalArgumentException("Desc type must be starting with L and ending with ;, but got $descType")
        }
        return descType.let { it.substring(1, it.length - 1) }
    }

    fun generateMethodDesc(method: Method): String {
        return generateMethodDesc(
            classToDescType(method.returnType),
            *method.parameterTypes.map { classToDescType(it) }.toTypedArray()
        )
    }

    fun generateMethodDesc(returnType: String, vararg argumentsTypes: String): String {
        return "(${argumentsTypes.joinToString("")})$returnType";
    }

    fun generateMethodDesc(constructor: Constructor<*>): String {
        return generateMethodDesc("V", *constructor.parameterTypes.map { it.getDescName() }.toTypedArray())
    }

    //<editor-fold desc="Method Call" defaultstate="collapsed">
    fun generateMethodCall(method: Method): MethodInsnNode {
        if (Modifier.isStatic(method.modifiers)) {
            if (method.declaringClass.isInterface) {
                return MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    method.declaringClass.name.replace(".", "/"),
                    method.name,
                    generateMethodDesc(method),
                    true
                )
            } else {
                return MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    method.declaringClass.name.replace(".", "/"),
                    method.name,
                    generateMethodDesc(method)
                )
            }
        } else {
            if (method.declaringClass.isInterface) {
                return MethodInsnNode(
                    Opcodes.INVOKEVIRTUAL,
                    method.declaringClass.name.replace(".", "/"),
                    method.name,
                    generateMethodDesc(method),
                    true
                )
            } else {
                return MethodInsnNode(
                    Opcodes.INVOKEVIRTUAL,
                    method.declaringClass.name.replace(".", "/"),
                    method.name,
                    generateMethodDesc(method)
                )
            }
        }
    }

    fun generateMethodCall(method: KFunction<*>): MethodInsnNode {
        val method1 = method.javaMethod ?: throw IllegalArgumentException("Could not get the java method")
        return generateMethodCall(method1)
    }
    //</editor-fold>

    //<editor-fold desc="Fields Set/Get" defaultstate="collapsed">
    fun generateGetField(field: Field): FieldInsnNode {
        return FieldInsnNode(if (Modifier.isStatic(field.modifiers)) Opcodes.GETSTATIC else Opcodes.GETFIELD, field.declaringClass.getJvmTypeName(), field.name, field.type.getDescName())
    }

    fun generatePutField(field: Field): FieldInsnNode {
        return FieldInsnNode(if (Modifier.isStatic(field.modifiers)) Opcodes.PUTSTATIC else Opcodes.PUTFIELD, field.declaringClass.getJvmTypeName(), field.name, field.type.getDescName())
    }

    fun generateGetField(field: KProperty<*>): MethodInsnNode {
        return generateMethodCall(field.getter)
    }

    fun generatePutField(field: KMutableProperty<*>): MethodInsnNode {
        return generateMethodCall(field.setter)
    }
    //</editor-fold>

    fun generateGetCompanion(type: Class<*>): FieldInsnNode {
        return generateGetField(type.getDeclaredField("Companion"))
    }
    inline fun <reified T> generateGetCompanion(): FieldInsnNode {
        return generateGetCompanion(T::class.java)
    }

    fun getReturnType(desc: String): String {
        return desc.split("(")[1]
    }

    fun getParameterTypes(desc: String): Array<String> {
        val reader = MethodArgumentsTypeReader(desc)
        return reader.arguments.toTypedArray()
    }

    inline fun <reified E> generateNewCall(varNumberManager: VarNumberManager, between: InsnList): InsnList {
        val clazz = E::class.java
        val constructor = clazz.constructors[0]
        var num = varNumberManager.allocateVariable()
        return InsnList().also {
            it.add(TypeInsnNode(Opcodes.NEW, clazz.name.replace(".", "/")))
            it.add(VarInsnNode(Opcodes.ASTORE, num))
            it.add(VarInsnNode(Opcodes.ALOAD, num))
            it.add(between)
            it.add(MethodInsnNode(Opcodes.INVOKESPECIAL, constructor.declaringClass.getJvmTypeName(), "<init>", generateMethodDesc(constructor)))
            it.add(VarInsnNode(Opcodes.ALOAD, num))
        }
    }
    inline fun <reified E> generateNewEventPost(between: InsnList = InsnList()): InsnList {
        SpookySky.Companion
        val clazz = E::class.java
        val constructor = clazz.constructors[0]
        return InsnList().also {
            it.addGetCompanion<SpookySky>()
            it.addGetField(SpookySky.Companion::INSTANCE)
            it.addGetField(SpookySky::eventManager)
            it.add(TypeInsnNode(Opcodes.NEW, clazz.name.replace(".", "/")))
            it.add(InsnNode(Opcodes.DUP))
            it.add(between)
            it.add(MethodInsnNode(Opcodes.INVOKESPECIAL, constructor.declaringClass.getJvmTypeName(), "<init>", generateMethodDesc(constructor)))
            it.addMethodCall(SimpleEventHandler::publish)
        }
    }

    class PatchedClassWriter(flags: Int) : ClassWriter(flags) {}


    fun Class<*>.getJvmTypeName(): String {
        return name.replace(".", "/")
    }
    fun Class<*>.getDescName(): String {
        return ASMUtils.classToDescType(this)
    }
    fun MethodNode.getParameterTypes(): Array<String> {
        return ASMUtils.getParameterTypes(desc)
    }
    fun InsnList.addMethodCall(method: KFunction<*>) {
        add(generateMethodCall(method))
    }
    fun InsnList.addMethodCall(method: Method) {
        add(generateMethodCall(method))
    }
    fun InsnList.addGetField(field: Field) {
        add(generateGetField(field))
    }
    fun InsnList.addPutField(field: Field) {
        add(generatePutField(field))
    }
    fun InsnList.addGetField(field: KProperty<*>) {
        add(generateGetField(field))
    }
    fun InsnList.addPutField(field: KMutableProperty<*>) {
        add(generatePutField(field))
    }
    fun InsnList.addGetCompanion(type: Class<*>) {
        add(generateGetCompanion(type))
    }
    inline fun <reified T> InsnList.addGetCompanion() {
        add(generateGetCompanion<T>())
    }

    fun ClassNode.getMethod(name: String, desc: String): MethodNode {
        return this.methods.first { it.name == name && it.desc == desc }
    }
    fun ClassNode.getMethod(node: MethodInsnNode): MethodNode {
        return getMethod(node.name, node.desc)
    }
}
