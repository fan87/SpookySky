package me.fan87.spookysky.client.module.impl.player

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PacketSentEvent
import me.fan87.spookysky.client.mapping.impl.packets.play.client.C03PacketPlayer
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module

class NoFall: Module("NoFall", "You will no longer take any fall damage", Category.PLAYER) {
    override fun onEnable() {

    }

    override fun onDisable() {

    }

    @EventHandler
    fun onPacket(event: PacketSentEvent) {
        val packet = event.packet
        if (packet is C03PacketPlayer) {
            packet.onGround = true
        }
    }
}