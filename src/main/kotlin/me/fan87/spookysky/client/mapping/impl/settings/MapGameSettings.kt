package me.fan87.spookysky.client.mapping.impl.settings


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrappedFieldType
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
    var keyBindForward by WrappedFieldType(MapGameSettings.mapKeyBindForward, KeyBinding::class.java)
    var keyBindLeft by WrappedFieldType(MapGameSettings.mapKeyBindLeft, KeyBinding::class.java)
    var keyBindBack by WrappedFieldType(MapGameSettings.mapKeyBindBack, KeyBinding::class.java)
    var keyBindRight by WrappedFieldType(MapGameSettings.mapKeyBindRight, KeyBinding::class.java)
    var keyBindJump by WrappedFieldType(MapGameSettings.mapKeyBindJump, KeyBinding::class.java)
    var keyBindSneak by WrappedFieldType(MapGameSettings.mapKeyBindSneak, KeyBinding::class.java)
    var keyBindSprint by WrappedFieldType(MapGameSettings.mapKeyBindSprint, KeyBinding::class.java)
    var keyBindInventory by WrappedFieldType(MapGameSettings.mapKeyBindInventory, KeyBinding::class.java)
    var keyBindUseItem by WrappedFieldType(MapGameSettings.mapKeyBindUseItem, KeyBinding::class.java)
    var keyBindDrop by WrappedFieldType(MapGameSettings.mapKeyBindDrop, KeyBinding::class.java)
    var keyBindAttack by WrappedFieldType(MapGameSettings.mapKeyBindAttack, KeyBinding::class.java)
    var keyBindPickBlock by WrappedFieldType(MapGameSettings.mapKeyBindPickBlock, KeyBinding::class.java)
    var keyBindChat by WrappedFieldType(MapGameSettings.mapKeyBindChat, KeyBinding::class.java)
    var keyBindPlayerList by WrappedFieldType(MapGameSettings.mapKeyBindPlayerList, KeyBinding::class.java)
    var keyBindCommand by WrappedFieldType(MapGameSettings.mapKeyBindCommand, KeyBinding::class.java)
    var keyBindScreenshot by WrappedFieldType(MapGameSettings.mapKeyBindScreenshot, KeyBinding::class.java)
    var keyBindTogglePerspective by WrappedFieldType(MapGameSettings.mapKeyBindTogglePerspective, KeyBinding::class.java)
    var keyBindSmoothCamera by WrappedFieldType(MapGameSettings.mapKeyBindSmoothCamera, KeyBinding::class.java)
    var keyBindFullscreen by WrappedFieldType(MapGameSettings.mapKeyBindFullscreen, KeyBinding::class.java)
    var keyBindSpectatorOutlines by WrappedFieldType(MapGameSettings.mapKeyBindSpectatorOutlines, KeyBinding::class.java)
    var keyBindStreamStartStop by WrappedFieldType(MapGameSettings.mapKeyBindStreamStartStop, KeyBinding::class.java)
    var keyBindStreamPauseUnpause by WrappedFieldType(MapGameSettings.mapKeyBindStreamPauseUnpause, KeyBinding::class.java)
    var keyBindStreamCommercials by WrappedFieldType(MapGameSettings.mapKeyBindStreamCommercials, KeyBinding::class.java)
    var keyBindStreamToggleMic by WrappedFieldType(MapGameSettings.mapKeyBindStreamToggleMic, KeyBinding::class.java)
}


class KeyBindMapping(val description: String): FieldMapping<Any, GameSettings>(MapGameSettings, "keyBind" + description.split(".")[1].let {
    it.substring(0, 1).uppercase() + it.substring(1)
}) {

}