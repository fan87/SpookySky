package me.fan87.spookysky.client.module.settings.impl

import com.google.gson.JsonObject
import me.fan87.spookysky.client.module.settings.Setting
import java.awt.Color

class ColorSetting(name: String, description: String, defaultValue: Color, val allowAlpha: Boolean = true) : Setting<Color>(name, description, defaultValue
) {
    override fun writeTo(json: JsonObject) {
        json.addProperty("value", value.rgb)
    }

    override fun readFrom(json: JsonObject) {
        value = Color(json["value"].asInt)
    }

    override fun isValueLegal(value: Color): Boolean {
        if (!allowAlpha && value.alpha != 255) {
            return false
        }
        return true
    }
}