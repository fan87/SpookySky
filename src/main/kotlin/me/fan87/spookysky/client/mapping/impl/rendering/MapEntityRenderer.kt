package me.fan87.spookysky.client.mapping.impl.rendering


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11

object MapEntityRenderer : ClassMapping<EntityRenderer>() {
    override fun getWrapperClass(): Class<EntityRenderer> {
        return EntityRenderer::class.java
    }

    override val humanReadableName: String
        get() = "EntityRenderer"

    val mapRenderWorldPass = MethodMapping<Unit, EntityRenderer>(this, "renderWorldPass(int, float, long)")
    val mapOrientCamera = MethodMapping<Unit, EntityRenderer>(this, "orientCamera(float)")
}

open class EntityRenderer protected constructor(original: Any) : WrapperClass(original) {

    fun orientCamera(partialTicks: Float) = MapEntityRenderer.mapOrientCamera.invoke(this, partialTicks)

    fun setupOverlayRendering() {
        GL11.glClear(256)
        GL11.glMatrixMode(5889)
        GL11.glLoadIdentity()
        GL11.glOrtho(
            0.0,
            Display.getWidth() * 1.0,
            Display.getHeight() * 1.0,
            0.0,
            1000.0,
            3000.0
        )
        GL11.glMatrixMode(5888)
        GL11.glLoadIdentity()
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f)
    }
    
}
