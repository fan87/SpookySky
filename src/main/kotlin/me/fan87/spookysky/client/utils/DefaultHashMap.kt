package me.fan87.spookysky.client.utils

class DefaultHashMap<K, V>(private val defaultValue: ()->V, m: MutableMap<out K, out V> = HashMap()): HashMap<K, V>(m) {

    override fun get(key: K): V {
        if (!containsKey(key)) {
            val default = defaultValue()
            this[key] = default
        }
        return super.get(key)!!
    }
}