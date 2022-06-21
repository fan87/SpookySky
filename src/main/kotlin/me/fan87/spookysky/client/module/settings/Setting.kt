package me.fan87.spookysky.client.module.settings

import com.google.gson.JsonObject

abstract class Setting<T>(val defaultValue: T) {

    var value: T = defaultValue
        set(value) {
            if (!isValueLegal(value)) {
                return
            }
            field = value
        }

    abstract fun writeTo(json: JsonObject)
    abstract fun readFrom(json: JsonObject)

    open fun isValueLegal(value: T): Boolean {
        return true
    }

}