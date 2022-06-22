package me.fan87.spookysky.client.module.settings.impl

import com.google.gson.JsonObject
import me.fan87.spookysky.client.module.settings.Setting

class IntSetting(name: String, description: String, defaultValue: Int, val minValue: Int, val maxValue: Int) : Setting<Int>(name, description, defaultValue) {
    override fun writeTo(json: JsonObject) {
        json.addProperty("value", value)
    }

    override fun readFrom(json: JsonObject) {
        value = json["double"].asInt
    }

    override fun isValueLegal(value: Int): Boolean {
        return value in minValue..maxValue
    }


}