package me.fan87.spookysky.client.module.settings.impl

import com.google.gson.JsonObject
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.settings.Setting
import kotlin.reflect.KClass

class EnumSetting<T: Enum<*>>(name: String, description: String, defaultValue: T, val enumClass: KClass<T>) : Setting<T>(name, description, defaultValue) {
    override fun writeTo(json: JsonObject) {
        json.addProperty("value", value.name)
    }

    override fun readFrom(json: JsonObject) {
        value = enumClass.java.enumConstants.first { it.name == json["value"].asString }
    }

    override fun isValueLegal(value: T): Boolean {
        return true
    }

    val values: List<T> by lazy {
        val out = ArrayList<T>()
        for (enumConstant in enumClass.java.enumConstants) {
            out.add(enumConstant as T)
        }
        out
    }
}