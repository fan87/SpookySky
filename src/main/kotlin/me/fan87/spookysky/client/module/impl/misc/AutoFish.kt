package me.fan87.spookysky.client.module.impl.misc

import kotlin.random.Random
import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.PacketReceivedEvent
import me.fan87.spookysky.client.events.events.WorldTickEvent
import me.fan87.spookysky.client.mapping.impl.packets.play.server.S29PacketSoundEffect
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.impl.BooleanSetting
import me.fan87.spookysky.client.module.settings.impl.IntSetting

class AutoFish: Module("AutoFish", "Use fishing rod automatically to fish", Category.MISC, true) {

    val delayMin = IntSetting("Delay Min", "Minimum delay to wait after something happens (Ticks)", 6, 0, 15)
    val delayMax = IntSetting("Delay Max", "Maximum delay to wait after something happens (Ticks)", 10, 0, 15)
    val refish = BooleanSetting("Re-fish", "Use the fishing rod after getting a fish", true)

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    var currentTime = 0
    var rightClickTime = -1
    var shouldContinue = false

    @EventHandler
    fun onTick(event: WorldTickEvent) {
        if (rightClickTime == currentTime) {
            mc.rightClickMouse()
            if (shouldContinue) {
                rightClickTime = currentTime + Random(System.currentTimeMillis()).nextInt(minOf(delayMin.value, delayMax.value), maxOf(delayMin.value, delayMax.value))
                shouldContinue = false
            } else {
                rightClickTime = -1;
            }
        }
        currentTime++
    }

    @EventHandler
    fun onPacket(event: PacketReceivedEvent) {
        val packet = event.packet
        if (packet is S29PacketSoundEffect) {
            SpookySky.addClientChat("${packet.soundName} - ${mc.thePlayer!!.getHeldItem()}")
            if (packet.soundName == "random.splash") {
                rightClickTime = currentTime + Random(System.currentTimeMillis()).nextInt(minOf(delayMin.value, delayMax.value), maxOf(delayMin.value, delayMax.value))
                shouldContinue = refish.value
            }
        }
    }

}