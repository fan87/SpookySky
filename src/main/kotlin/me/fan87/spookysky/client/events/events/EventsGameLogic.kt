package me.fan87.spookysky.client.events.events

import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.packets.Packet

class ClientTickEvent
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
class EntityJumpEvent(val entity: Entity) {
    var cancelled = false
}