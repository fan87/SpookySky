package me.fan87.spookysky.client.mapping.impl.entities

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.packets.NetHandlerPlayClient

object MapEntityPlayerSP: ClassMapping<EntityPlayerSP>() {
    override fun getWrapperClass(): Class<EntityPlayerSP> {
        return EntityPlayerSP::class.java
    }

    override val humanReadableName: String
        get() = "EntityPlayerSP"

    val mapOnUpdateWalkingPlayer = MethodMapping<Unit, EntityPlayerSP>(this, "onUpdateWalkingPlayer()")
    val mapSendChatMessage = MethodMapping<Unit, EntityPlayerSP>(this, "sendChatMessage(String)")

    val mapSendQueue = FieldMapping<Any, EntityPlayerSP>(this, "sendQueue")

}

class EntityPlayerSP private constructor(original: Any): EntityLivingBase(original) {

    val sendQueue by WrappedFieldType(MapEntityPlayerSP.mapSendQueue, NetHandlerPlayClient::class.java)

    fun onUpdateWalkingPlayer() = MapEntityPlayerSP.mapOnUpdateWalkingPlayer.invoke(this)
    fun sendChatMessage(message: String) = MapEntityPlayerSP.mapSendChatMessage.invoke(this, message)

}