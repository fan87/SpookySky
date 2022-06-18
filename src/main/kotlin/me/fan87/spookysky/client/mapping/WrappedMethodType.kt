package me.fan87.spookysky.client.mapping

import kotlin.reflect.KProperty

class WrappedMethodType<WrapperType: WrapperClass, OwnerType: WrapperClass>(val mapping: MethodMapping<Any, OwnerType>, val wrapperClass: Class<WrapperType>) {


    operator fun invoke(instance: OwnerType?, vararg args: Any): WrapperType? {

        val returnValue = mapping.getJavaMethod().invoke(instance?.original, *args)
        if (returnValue == null) {
            return null
        }
        return wrapperClass.constructors[0].newInstance(returnValue) as WrapperType?
    }

}