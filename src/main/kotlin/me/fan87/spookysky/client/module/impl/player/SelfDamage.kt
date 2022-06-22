package me.fan87.spookysky.client.module.impl.player

import me.fan87.spookysky.client.mapping.impl.packets.play.client.C04PacketPlayerPosition
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.IntSetting

class SelfDamage: Module("SelfDamage", "Damage yourself to bypass something", Category.PLAYER) {

    val damage: IntSetting = IntSetting("Amount", "The amount of damage you want to take", 1, 1..60)

    override fun onEnable() {
        toggled = false
        val thePlayer = mc.thePlayer
        if (thePlayer != null) {
            thePlayer.sendQueue.addToSendQueue(C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, true))
            thePlayer.sendQueue.addToSendQueue(C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
            var remaining = damage.value
            var currentY = 3
            var currentMoved = 3
            while (remaining > 0) {
                thePlayer.sendQueue.addToSendQueue(C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY + currentY + remaining.coerceAtMost(8 - currentMoved), thePlayer.posZ, false))
                remaining -= remaining.coerceAtMost(8 - currentMoved)
                currentMoved = 0
            }
            thePlayer.sendQueue.addToSendQueue(C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false))
        }

    }

    override fun onDisable() {
    }
}