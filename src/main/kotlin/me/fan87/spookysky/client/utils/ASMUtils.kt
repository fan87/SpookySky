package me.fan87.spookysky.client.utils

import me.fan87.regbex.PrimitiveType
import me.fan87.regbex.utils.MethodArgumentsTypeReader
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.EventsManager
import me.fan87.spookysky.client.mapping.MappedMethodInfo
import me.fan87.spookysky.client.mapping.MethodMapping
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.TypeInsnNode
import org.objectweb.asm.tree.VarInsnNode
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaMethod

object ASMUtils {

    private val classes = HashMap<String, Class<*>>()

    fun getClassByName(name: String): Class<*> {
        val value = classes[name]
        if (value == null) {
            val clazz = Class.forName(name, false, SpookySky.INSTANCE.clientClassLoader)
            classes[name] = clazz
            return clazz
        }
        return value
    }

    fun parseClass(data: ByteArray): ClassNode {
        val reader = ClassReader(data)
        val classNode = ClassNode()
        reader.accept(classNode, ClassReader.EXPAND_FRAMES)
        return classNode
    }


    fun writeClass(node: ClassNode): ByteArray {
        val writer = PatchedClassWriter0(ClassWriter.COMPUTE_FRAMES)
        node.accept(writer)
        return writer.toByteArray()
    }
    fun writeClassNoVerify(node: ClassNode): ByteArray {
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

    fun jvmTypeToClass(name: String): Class<*> {
        return getClassByName(name.replace("/", "."))
    }

    fun descTypeToClass(name: String): Class<*> {
        for (value in PrimitiveType.values()) {
            if (value.jvmName == name) {
                return value.primitiveType
            }
        }
        if (name.startsWith("L") && name.endsWith(";")) {
            return getClassByName(name.replace("/", ".").let { it.substring(1, it.length - 1) })
        }
        return getClassByName(name.replace("/", "."))
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

    inline fun <reified T> generateGetInstance(): FieldInsnNode {
        return generateGetField(T::class.java.fields.first { it.name == "INSTANCE" })
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
        return desc.split(")")[1]
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
            it.addMethodCall(EventsManager::post)
        }
    }
    inline fun <reified E> generateNewEventPostAndPushToStack(between: InsnList = InsnList(), varNumberManager: VarNumberManager): InsnList {
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
            it.add(InsnNode(Opcodes.DUP))
            var number = varNumberManager.allocateVariable()
            it.add(VarInsnNode(Opcodes.ASTORE, number))
            it.addMethodCall(EventsManager::post)
            it.add(VarInsnNode(Opcodes.ALOAD, number))
        }
    }

    class PatchedClassWriter(flags: Int) : ClassWriter(flags) {

        override fun getCommonSuperClass(type1: String?, type2: String?): String {
            return "java/lang/Object"
        }
    }
    class PatchedClassWriter0(flags: Int) : ClassWriter(flags) {}


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
    fun InsnList.addThis() {
        add(VarInsnNode(Opcodes.ALOAD, 0))
    }
    fun InsnList.insertInstructions(inclusiveStart: Int, instructions: Iterable<AbstractInsnNode>): InsnList {
        val out = InsnList()
        for (withIndex in this.withIndex()) {
            if (withIndex.index == inclusiveStart) {
                for (instruction in instructions) {
                    out.add(instruction)
                }
            }
            out.add(withIndex.value)
        }
        return out
    }
    fun InsnList.insertInstructions(inclusiveStart: Int, instructions: (InsnList) -> Unit): InsnList {
        val insnList = InsnList()
        instructions(insnList)
        return insertInstructions(inclusiveStart, insnList)
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
    fun ClassNode.getMethod(info: MappedMethodInfo): MethodNode {
        return getMethod(info.name, info.desc)
    }
    fun ClassNode.getMethod(mapping: MethodMapping<*, *>): MethodNode {
        return getMethod(mapping.assumeMapped().name, mapping.assumeMapped().desc)
    }
}
