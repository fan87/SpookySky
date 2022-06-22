package me.fan87.spookysky.client.module.settings.impl

import com.google.gson.JsonObject
import me.fan87.spookysky.client.module.settings.Setting

class DoubleSetting(name: String, description: String, defaultValue: Double, val minValue: Double, val maxValue: Double) : Setting<Double>(name, description, defaultValue) {
    override fun writeTo(json: JsonObject) {
        json.addProperty("value", value)
    }

    override fun readFrom(json: JsonObject) {
        value = json["double"].asDouble
    }

    override fun isValueLegal(value: Double): Boolean {
        return value in minValue..maxValue
    }


}