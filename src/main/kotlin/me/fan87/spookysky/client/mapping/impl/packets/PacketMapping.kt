package me.fan87.spookysky.client.mapping.impl.packets

import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping

abstract class PacketMapping<T: Packet>: ClassMapping<T>() {

    abstract val id: Int
    abstract val mode: PacketSource

    open fun getDataOrder(): List<FieldMapping<*, *>> {
        return arrayListOf()
    }
}

enum class PacketSource {
    PLAY_CLIENT,
    PLAY_SERVER
}