package me.fan87.spookysky.client.mapping.impl.entities


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapPlayerCapabilities : ClassMapping<PlayerCapabilities>() {
    override fun getWrapperClass(): Class<PlayerCapabilities> {
        return PlayerCapabilities::class.java
    }

    override val humanReadableName: String
        get() = "PlayerCapabilities"

    val mapWriteCapabilitiesToNBT = MethodMapping<Unit, PlayerCapabilities>(this, "writeCapabilitiesToNBT(NBTComponent)")

    val mapDisableDamage = AbilityFieldMapping<Boolean>("disableDamage", "invulnerable")
    val mapIsFlying = AbilityFieldMapping<Boolean>("isFlying", "flying")
    val mapAllowFlying = AbilityFieldMapping<Boolean>("allowFlying", "mayfly")
    val mapIsCreativeMode = AbilityFieldMapping<Boolean>( "isCreativeMode", "instabuild")
    val mapAllowEdit = AbilityFieldMapping<Boolean>("allowEdit", "mayBuild")
    val mapFlySpeed = AbilityFieldMapping<Float>("flySpeed", "flySpeed")
    val mapWalkSpeed = AbilityFieldMapping<Float>("walkSpeed", "walkSpeed")

}

open class PlayerCapabilities protected constructor(original: Any) : WrapperClass(original) {
    var disableDamage by MapPlayerCapabilities.mapDisableDamage
    var isFlying by MapPlayerCapabilities.mapIsFlying
    var allowFlying by MapPlayerCapabilities.mapAllowFlying
    var isCreativeMode by MapPlayerCapabilities.mapIsCreativeMode
    var allowEdit by MapPlayerCapabilities.mapAllowEdit
    var flySpeed by MapPlayerCapabilities.mapFlySpeed
    var walkSpeed by MapPlayerCapabilities.mapWalkSpeed
}


// so we could use `for` to loop through all ability field mappings
class AbilityFieldMapping<FieldType>(name: String, val nbtName: String):
    FieldMapping<FieldType, PlayerCapabilities>(MapPlayerCapabilities, name)