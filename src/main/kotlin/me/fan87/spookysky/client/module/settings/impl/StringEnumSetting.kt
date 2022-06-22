package me.fan87.spookysky.client.module.settings.impl

import com.google.gson.JsonObject
import me.fan87.spookysky.client.module.settings.Setting

open class StringEnumSetting(name: String, description: String, defaultValue: String, vararg val values: String) : Setting<String>(name, description, defaultValue) {
    override fun writeTo(json: JsonObject) {
        json.addProperty("value", value)
    }

    override fun readFrom(json: JsonObject) {
        value = json["value"].asString
    }

    override fun isValueLegal(value: String): Boolean {
        return value in values
    }

}