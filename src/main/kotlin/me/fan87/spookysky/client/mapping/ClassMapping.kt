package me.fan87.spookysky.client.mapping

abstract class ClassMapping<T>: Mapping<MappedClassInfo>() {

    val children = ArrayList<MemberMapping<*>>()

    fun getJavaClass(): Class<*> {
        return Class.forName(checkMapped().name.replace("/", "."))
    }


    fun map(className: String) {
        mapped = MappedClassInfo(className)
    }

}