package me.fan87.spookysky.client.mapping.impl.item


import me.fan87.spookysky.client.mapping.*

object MapItemStack : ClassMapping<ItemStack>() {
    override fun getWrapperClass(): Class<ItemStack> {
        return ItemStack::class.java
    }

    override val humanReadableName: String
        get() = "ItemStack"

    val mapItem = FieldMapping<Any, ItemStack>(this, "item")
    val mapGetDisplayName = MethodMapping<String, ItemStack>(this, "getDisplayName")

}

open class ItemStack protected constructor(original: Any) : WrapperClass(original) {

    val item by WrappedFieldType(MapItemStack.mapItem, Item::class.java)

    fun getDisplayName() = MapItemStack.mapGetDisplayName.invoke(this)!!

}
