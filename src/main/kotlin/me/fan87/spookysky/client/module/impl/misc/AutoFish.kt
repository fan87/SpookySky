package me.fan87.spookysky.client.module.impl.misc

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
    var fishTime = -1

    @EventHandler
    fun onTick(event: WorldTickEvent) {
        //
    }

    @EventHandler
    fun onPacket(event: PacketReceivedEvent) {
        val packet = event.packet
        if (packet is S29PacketSoundEffect) {
            SpookySky.addClientChat(packet.soundName)
            if (packet.soundName == "random.splash") {
                mc.rightClickMouse()
                mc.rightClickMouse()
            }
        }
    }

}