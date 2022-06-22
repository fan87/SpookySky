package me.fan87.spookysky.client.events.events

import me.fan87.spookysky.client.exception.MissingMappingException
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.packets.Packet

class ClientTickEvent
class WorldTickEvent
class PacketSentEvent(val packet: Packet) {
    var cancelled = false
}
class PacketReceivedEvent(val packet: Packet) {
    var cancelled = false
}
class ClickMouseEvent {
    var cancelled = false
}
class RightClickMouseEvent {
    var cancelled = false
}
class EntityJumpEvent(entity0: Any): Event() {

    private var entity0: Entity? = null
    val entity: Entity
        get() = entity0!!

    init {
        try {
            this.entity0 = MappingsManager.getWrapped(entity0)
        } catch (_: MissingMappingException) {
            cancelledSending = true
        }
    }

    var cancelled = false
}