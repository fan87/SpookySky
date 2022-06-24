package me.fan87.spookysky.client.events.events

import me.fan87.spookysky.client.exception.MissingMappingException
import me.fan87.spookysky.client.mapping.MappingsManager
import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.rendering.RenderLivingEntity

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
class Render2DPreBossBarEvent()
class RenderEndFrameEvent()
class RenderEntityModelEvent(renderOriginal: Any, entityOriginal: Any, val renderLimbSwing: Float, val renderLimbSwingAmount: Float, val renderAgeInTicks: Float, val renderHeadYaw: Float, val renderHeadPitch: Float, val renderScaleFactor: Float): Event() {
    private var entity1: Entity? = null
    private var render1: RenderLivingEntity<*>? = null

    init {
        try {
            entity1 = MappingsManager.getWrapped(entityOriginal)
            render1 = MappingsManager.getWrapped(renderOriginal)
        } catch (_: MissingMappingException) {
            cancelledSending = true
        }
    }

    fun renderModel() {
        this.render.mainModel.render(this.entity, this.renderLimbSwing, this.renderLimbSwingAmount, this.renderAgeInTicks, this.renderHeadYaw, this.renderHeadPitch, this.renderScaleFactor)

    }
    
    val render: RenderLivingEntity<*>
        get() = render1!!

    val entity: Entity
        get() = entity1!!

    var cancelled = false
}
class PostRenderEntityModelEvent(renderOriginal: Any, entityOriginal: Any, val renderLimbSwing: Float, val renderLimbSwingAmount: Float, val renderAgeInTicks: Float, val renderHeadYaw: Float, val renderHeadPitch: Float, val renderScaleFactor: Float): Event() {
    private var entity1: Entity? = null
    private var render1: RenderLivingEntity<*>? = null

    init {
        try {
            entity1 = MappingsManager.getWrapped(entityOriginal)
            render1 = MappingsManager.getWrapped(renderOriginal)
        } catch (_: MissingMappingException) {
            cancelledSending = true
        }
    }

    val render: RenderLivingEntity<*>
        get() = render1!!

    val entity: Entity
        get() = entity1!!


}
class CrossHairEvent() {
    var cancelled = false
}