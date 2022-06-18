package me.fan87.spookysky.client.mapping


abstract class MemberMapping<T: MappedMemberInfo>(
    val parent: ClassMapping<*>,
    private val name: String
): Mapping<T>() {

    override val humanReadableName: String
        get() = name

    init {
        parent.children.add(this)
    }


}