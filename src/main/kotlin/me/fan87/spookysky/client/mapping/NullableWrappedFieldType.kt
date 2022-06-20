package me.fan87.spookysky.client.mapping

import kotlin.reflect.KProperty

class NullableWrappedFieldType<WrapperType: WrapperClass, OwnerType: WrapperClass>(val mapping: NullableFieldMapping<Any, OwnerType>, val wrapperType: Class<WrapperType>) {
    operator fun getValue(thisRef: OwnerType?, property: KProperty<*>): WrapperType? {
        val value = mapping.getValue(thisRef, property)
        if (value == null) {
            return null
        }
        return MappingsManager.getWrapped(value)
    }

    operator fun setValue(thisRef: OwnerType?, property: KProperty<*>, value: WrapperType?) {
        mapping.setValue(thisRef, property, value?.original)
    }

}