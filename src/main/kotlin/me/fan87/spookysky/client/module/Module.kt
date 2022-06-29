package me.fan87.spookysky.client.module

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.events.events.ModuleToggleEvent
import me.fan87.spookysky.client.mapping.Mapping
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.module.settings.Setting
import me.fan87.spookysky.client.module.settings.impl.KeySetting
import kotlin.reflect.KProperty1
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

abstract class Module(val name: String, val description: String, val category: Category, val ghost: Boolean = category == Category.LEGIT || category == Category.RENDER) {

    val key = KeySetting("KeyBind", "When you press the key, the module will be toggled")

    var toggled = false
        set(value) {
            if (value != field) {
                if (value) {
                    if (dependencies.any { !it.isMapped() }) {
                        SpookySky.debug("[Module] [$name] Dependency\"${dependencies.first { !it.isMapped() }.humanReadableName}\" has not been solved yet, could not enable the module.")
                        return
                    }
                    val event = ModuleToggleEvent(this, false)
                    SpookySky.INSTANCE.eventManager.post(event)
                    if (event.cancelled) {
                        return
                    }
                    spookySky.eventManager.registerListener(this)
                    field = value
                    onEnable()
                } else {
                    val event = ModuleToggleEvent(this, true)
                    SpookySky.INSTANCE.eventManager.post(event)
                    if (event.cancelled) {
                        return
                    }
                    spookySky.eventManager.unregisterListener(this)
                    field = value
                    onDisable()
                }
            }
        }

    val spookySky: SpookySky
        get() = SpookySky.INSTANCE

    val mc: Minecraft
        get() = Minecraft.getMinecraft()

    private val dependencies = ArrayList<Mapping<*>>()
    fun dependsOn(map: Mapping<*>) {
        dependencies.add(map)
    }

    protected abstract fun onEnable()
    protected abstract fun onDisable()


    val settings = ArrayList<Setting<*>>()

    fun postInit() {
        val kClass = this::class
        for (memberProperty in kClass.memberProperties) {
            if (memberProperty.returnType.isSubtypeOf(Setting::class.createType(arguments = arrayListOf(KTypeProjection.STAR)))) {
                settings.add((memberProperty as KProperty1<Module, Setting<*>>).get(this))
            }
        }
    }


}