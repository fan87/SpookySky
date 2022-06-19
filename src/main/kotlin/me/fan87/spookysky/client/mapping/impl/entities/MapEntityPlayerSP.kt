package me.fan87.spookysky.client.mapping.impl.entities

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.mapping.impl.Minecraft

object MapEntityPlayerSP: ClassMapping<EntityPlayerSP>() {
    override val humanReadableName: String
        get() = "EntityPlayerSP"

    val mapOnUpdateWalkingPlayer = MethodMapping<Unit, EntityPlayerSP>(this, "onUpdateWalkingPlayer()")
    val mapSendChatMessage = MethodMapping<Unit, EntityPlayerSP>(this, "sendChatMessage(String)")
}

class EntityPlayerSP(original: Any): Entity(original) {

    fun onUpdateWalkingPlayer() = MapEntityPlayerSP.mapOnUpdateWalkingPlayer.invoke(this)
    fun sendChatMessage(message: String) = MapEntityPlayerSP.mapSendChatMessage.invoke(this, message)

}