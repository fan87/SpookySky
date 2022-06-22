package me.fan87.spookysky.client.module.settings.impl

import com.google.gson.JsonObject
import me.fan87.spookysky.client.module.settings.Setting

class BooleanSetting(name: String, description: String, defaultValue: Boolean) : Setting<Boolean>(name, description, defaultValue) {
    override fun writeTo(json: JsonObject) {
        json.addProperty("value", value)
    }

    override fun readFrom(json: JsonObject) {
        value = json["value"].asBoolean
    }

}