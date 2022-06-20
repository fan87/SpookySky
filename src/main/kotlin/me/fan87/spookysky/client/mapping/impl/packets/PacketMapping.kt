package me.fan87.spookysky.client.mapping.impl.packets

import me.fan87.spookysky.client.mapping.ClassMapping

abstract class PacketMapping<T: Packet>: ClassMapping<T>() {

    abstract val id: Int
    abstract val mode: PacketSource
}

enum class PacketSource {
    PLAY_CLIENT,
    PLAY_SERVER
}