package me.fan87.spookysky.client.render

import me.fan87.spookysky.client.mapping.impl.rendering.MapRender
import me.fan87.spookysky.client.mapping.impl.rendering.Render

object RenderStateManager {

    @JvmStatic
    var renderShadow: Boolean = true
    @JvmStatic
    var renderNameTag: Boolean = true

    @JvmStatic
    fun canRenderName(render: Any, entity: Any): Boolean {
        println("Can Render Name: ${entity}")
        return false
//        if (!renderNameTag) {
//            return false
//        }
//        return MapRender.mapCanRenderName.getJavaMethod().invoke(render, entity) as Boolean
    }

}