package me.fan87.spookysky.client.mapping.impl.item


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrappedFieldType
import me.fan87.spookysky.client.mapping.WrapperClass

object MapItemStack : ClassMapping<ItemStack>() {
    override fun getWrapperClass(): Class<ItemStack> {
        return ItemStack::class.java
    }

    override val humanReadableName: String
        get() = "ItemStack"

    val mapItem = FieldMapping<Any, ItemStack>(this, "item")

}

open class ItemStack protected constructor(original: Any) : WrapperClass(original) {

    val item by WrappedFieldType(MapItemStack.mapItem, Item::class.java)

}
