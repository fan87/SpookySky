package me.fan87.spookysky.client.mapping

import kotlin.reflect.KProperty

class WrappedFieldType<WrapperType: WrapperClass, OwnerType: WrapperClass>(val mapping: FieldMapping<Any, OwnerType>, val wrapperType: Class<WrapperType>) {
    operator fun getValue(thisRef: OwnerType?, property: KProperty<*>): WrapperType? {
        val value = mapping.getValue(thisRef, property)
        if (value == null) {
            return null
        }
        return wrapperType.constructors[0].newInstance(value) as WrapperType?
    }

    operator fun setValue(thisRef: OwnerType?, property: KProperty<*>, value: WrapperType?) {
        mapping.setValue(thisRef, property, value?.original)
    }

}