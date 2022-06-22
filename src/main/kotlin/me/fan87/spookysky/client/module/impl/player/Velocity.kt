package me.fan87.spookysky.client.module.impl.player

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PacketReceivedEvent
import me.fan87.spookysky.client.mapping.impl.packets.play.server.S12PacketEntityVelocity
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.DoubleSetting

class Velocity: Module("Velocity", "Modifies the velocities values", Category.PLAYER) {

    val horizontal = DoubleSetting("Horizontal", "Horizontal velocity will be multiplied by this value", 0.0, 0.0, 200.0)
    val vertical = DoubleSetting("Vertical", "Vertical velocity will be multiplied by this value", 0.0, 0.0, 200.0)

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    @EventHandler
    fun onVelocity(event: PacketReceivedEvent) {
        val packet = event.packet
        if (packet is S12PacketEntityVelocity) {
            if (packet.entityId == mc.thePlayer!!.entityId) {
                packet.motionX = (packet.motionY*(horizontal.value/100)).toInt().toShort()
                packet.motionZ = (packet.motionY*(horizontal.value/100)).toInt().toShort()
                packet.motionY = (packet.motionY*(vertical.value/100)).toInt().toShort()
            }
        }
    }
}