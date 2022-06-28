package me.fan87.spookysky.client.mapping

abstract class MappedInfo(val name: String)
class MappedClassInfo(name: String): MappedInfo(name) {
    fun getDescName(): String {
        return "L$name;"
    }

    override fun toString(): String {
        return name
    }
}
open class MappedMemberInfo(name: String, val desc: String): MappedInfo(name) {

    override fun toString(): String {
        return name + desc
    }
}
class MappedFieldInfo(name: String, desc: String): MappedMemberInfo(name, desc)
class MappedMethodInfo(name: String, desc: String): MappedMemberInfo(name, desc)