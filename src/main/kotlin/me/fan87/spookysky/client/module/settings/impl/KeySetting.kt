package me.fan87.spookysky.client.module.settings.impl

import com.google.gson.JsonObject
import me.fan87.spookysky.client.module.settings.Setting
import org.lwjgl.input.Keyboard

class KeySetting(name: String, description: String, defaultValue: Int = Keyboard.KEY_NONE) : Setting<Int>(name, description, defaultValue) {
    override fun writeTo(json: JsonObject) {
        json.addProperty("value", json.asInt)
    }

    override fun readFrom(json: JsonObject) {
        json["value"].asInt
    }
}