package me.fan87.spookysky.client.mapping.impl.packets


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapNetHandlerPlayClient : ClassMapping<NetHandlerPlayClient>() {
    override fun getWrapperClass(): Class<NetHandlerPlayClient> {
        return NetHandlerPlayClient::class.java
    }

    override val humanReadableName: String
        get() = "NetHandlerPlayClient"

    val mapAddToSendQueue = MethodMapping<Unit, NetHandlerPlayClient>(this, "addToSendQueue(Packet)")
}

open class NetHandlerPlayClient protected constructor(original: Any) : WrapperClass(original) {

    fun addToSendQueue(packet: Packet) = MapNetHandlerPlayClient.mapAddToSendQueue.invoke(this, packet.original)

}
