package me.fan87.spookysky.client.mapping.impl.entities


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.WrapperClass
import me.fan87.spookysky.client.mapping.impl.item.ItemStack

object MapEntityLiving : ClassMapping<EntityLiving>() {
    override fun getWrapperClass(): Class<EntityLiving> {
        return EntityLiving::class.java
    }

    override val humanReadableName: String
        get() = "EntityLiving"

    val mapEquipment = FieldMapping<Array<Any>, EntityLiving>(this, "equipment")


}

open class EntityLiving protected constructor(original: Any) : EntityLivingBase(original) {
    val equipment: Array<ItemStack>
        get() {
            val list = ArrayList<ItemStack>()
            for (any in MapEntityLiving.mapEquipment.getJavaField().get(original) as Array<Any>) {
                list.add(MappingsManager.getWrapped(any))
            }
            return list.toTypedArray()
        }
}
