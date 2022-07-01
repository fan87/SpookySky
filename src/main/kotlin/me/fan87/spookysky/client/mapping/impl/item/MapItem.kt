package me.fan87.spookysky.client.mapping.impl.item


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapItem : ClassMapping<Item>() {
    override fun getWrapperClass(): Class<Item> {
        return Item::class.java
    }

    override val humanReadableName: String
        get() = "Item"

    val mapUnlocalizedName = FieldMapping<String, Item>(this, "unlocalizedName")
    val mapGetUnlocalizedName = MethodMapping<String, Item>(this, "getUnlocalizedName()")
    val mapGetUnlocalizedName_ItemStack = MethodMapping<String, Item>(this, "getUnlocalizedName(ItemStack)")
    val mapGetItemStackDisplayName = MethodMapping<String, Item>(this, "getItemStackDisplayName(ItemStack)")

}

open class Item protected constructor(original: Any) : WrapperClass(original) {

    fun getUnlocalizedName(): String = MapItem.mapGetUnlocalizedName.invoke(this)!!
    fun getUnlocalizedName(item: ItemStack): String = MapItem.mapGetUnlocalizedName_ItemStack.invoke(this, item.original)!!
    fun getItemStackDisplayName(item: ItemStack): String = MapItem.mapGetItemStackDisplayName.invoke(this, item.original)!!

}
