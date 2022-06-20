package me.fan87.spookysky.client.mapping.impl.settings


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapGameSettings : ClassMapping<GameSettings>() {
    override fun getWrapperClass(): Class<GameSettings> {
        return GameSettings::class.java
    }

    override val humanReadableName: String
        get() = "GameSettings"

    var mapKeyBindForward = KeyBindMapping("key.forward")
    var mapKeyBindLeft = KeyBindMapping("key.left")
    var mapKeyBindBack = KeyBindMapping("key.back")
    var mapKeyBindRight = KeyBindMapping("key.right")
    var mapKeyBindJump = KeyBindMapping("key.jump")
    var mapKeyBindSneak = KeyBindMapping("key.sneak")
    var mapKeyBindSprint = KeyBindMapping("key.sprint")
    var mapKeyBindInventory = KeyBindMapping("key.inventory")
    var mapKeyBindUseItem = KeyBindMapping("key.use")
    var mapKeyBindDrop = KeyBindMapping("key.drop")
    var mapKeyBindAttack = KeyBindMapping("key.attack")
    var mapKeyBindPickBlock = KeyBindMapping("key.pickItem")
    var mapKeyBindChat = KeyBindMapping("key.chat")
    var mapKeyBindPlayerList = KeyBindMapping("key.playerlist")
    var mapKeyBindCommand = KeyBindMapping("key.command")
    var mapKeyBindScreenshot = KeyBindMapping("key.screenshot")
    var mapKeyBindTogglePerspective = KeyBindMapping("key.togglePerspective")
    var mapKeyBindSmoothCamera = KeyBindMapping("key.smoothCamera")
    var mapKeyBindFullscreen = KeyBindMapping("key.fullscreen")
    var mapKeyBindSpectatorOutlines = KeyBindMapping("key.spectatorOutlines")
    var mapKeyBindStreamStartStop = KeyBindMapping("key.streamStartStop")
    var mapKeyBindStreamPauseUnpause = KeyBindMapping("key.streamPauseUnpause")
    var mapKeyBindStreamCommercials = KeyBindMapping("key.streamCommercial")
    var mapKeyBindStreamToggleMic = KeyBindMapping("key.streamToggleMic")


}

open class GameSettings protected constructor(original: Any) : WrapperClass(original) {
    var keyBindForward by MapGameSettings.mapKeyBindForward
    var keyBindLeft by MapGameSettings.mapKeyBindLeft
    var keyBindBack by MapGameSettings.mapKeyBindBack
    var keyBindRight by MapGameSettings.mapKeyBindRight
    var keyBindJump by MapGameSettings.mapKeyBindJump
    var keyBindSneak by MapGameSettings.mapKeyBindSneak
    var keyBindSprint by MapGameSettings.mapKeyBindSprint
    var keyBindInventory by MapGameSettings.mapKeyBindInventory
    var keyBindUseItem by MapGameSettings.mapKeyBindUseItem
    var keyBindDrop by MapGameSettings.mapKeyBindDrop
    var keyBindAttack by MapGameSettings.mapKeyBindAttack
    var keyBindPickBlock by MapGameSettings.mapKeyBindPickBlock
    var keyBindChat by MapGameSettings.mapKeyBindChat
    var keyBindPlayerList by MapGameSettings.mapKeyBindPlayerList
    var keyBindCommand by MapGameSettings.mapKeyBindCommand
    var keyBindScreenshot by MapGameSettings.mapKeyBindScreenshot
    var keyBindTogglePerspective by MapGameSettings.mapKeyBindTogglePerspective
    var keyBindSmoothCamera by MapGameSettings.mapKeyBindSmoothCamera
    var keyBindFullscreen by MapGameSettings.mapKeyBindFullscreen
    var keyBindSpectatorOutlines by MapGameSettings.mapKeyBindSpectatorOutlines
    var keyBindStreamStartStop by MapGameSettings.mapKeyBindStreamStartStop
    var keyBindStreamPauseUnpause by MapGameSettings.mapKeyBindStreamPauseUnpause
    var keyBindStreamCommercials by MapGameSettings.mapKeyBindStreamCommercials
    var keyBindStreamToggleMic by MapGameSettings.mapKeyBindStreamToggleMic
}


class KeyBindMapping(val description: String): FieldMapping<Any, GameSettings>(MapGameSettings, "keyBind" + description.split(".")[1].let {
    it.substring(0, 1).uppercase() + it.substring(1)
}) {

}