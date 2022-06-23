package me.fan87.spookysky.client.events.events

import me.fan87.spookysky.client.exception.MissingMappingException
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.impl.entities.Entity

class RenderEntityEvent(original: Any): Event() {

    private var entity1: Entity? = null

    init {
        try {
            entity1 = MappingsManager.getWrapped(original)
        } catch (_: MissingMappingException) {
            cancelledSending = true
        }
    }
    val entity: Entity
        get() = entity1!!

    var cancelled = false
}
class PostRender3DEvent(val partialTicks: Float)
class CrossHairEvent() {
    var cancelled = false
}