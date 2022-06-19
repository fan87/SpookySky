package me.fan87.spookysky.client.mapping

import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.FieldNode
import java.lang.reflect.Field
import kotlin.reflect.KProperty

class NullableFieldMapping<FieldType: Any?, OwnerType: WrapperClass>(parent: ClassMapping<OwnerType>, name: String): MemberMapping<MappedFieldInfo>(parent, name) {

    operator fun getValue(thisRef: OwnerType?, property: KProperty<*>): FieldType? {
        return getJavaField().get(thisRef?.original) as FieldType
    }

    operator fun setValue(thisRef: OwnerType?, property: KProperty<*>, value: FieldType?) {
        getJavaField().set(thisRef?.original, value)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): FieldType? {
        return getJavaField().get(thisRef) as FieldType
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: FieldType?) {
        getJavaField().set(thisRef, value)
    }

    fun getJavaField(): Field {
        return parent.getJavaClass().getDeclaredField(assumeMapped().name).apply { isAccessible = true }
    }


    fun map(fieldName: String, desc: String) {
        mapped = MappedFieldInfo(fieldName, desc)
    }

    fun map(node: FieldNode) {
        mapped = MappedFieldInfo(node.name, node.desc)
    }

    fun map(node: FieldInsnNode) {
        mapped = MappedFieldInfo(node.name, node.desc)
    }

}