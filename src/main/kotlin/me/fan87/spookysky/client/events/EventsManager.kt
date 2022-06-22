package me.fan87.spookysky.client.events

import me.fan87.spookysky.client.events.events.ClientTickEvent
import me.fan87.spookysky.client.events.events.PostRender3DEvent
import me.fan87.spookysky.client.events.events.WorldTickEvent
import me.fan87.spookysky.client.exception.MissingMappingException
import me.fan87.spookysky.client.mapping.impl.Minecraft
import java.lang.reflect.Method
import java.util.Comparator

class EventsManager {

    init {
        registerListener(this)
    }

    @EventHandler
    fun worldTicKEventHook(event: ClientTickEvent) {
        try {
            if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
                post(WorldTickEvent())
            }
        } catch (_: MissingMappingException) {}
    }

    private val listeners = ArrayList<Any>()

    fun post(event: Any) {
        val toCall = LinkedHashMap<Method, Any>()
        for (listener in listeners) {
            for (method in listener.javaClass.methods) {
                if (method.isAnnotationPresent(EventHandler::class.java) && event.javaClass.isAssignableFrom(method.parameterTypes[0])) {
                    toCall[method] = listener
                }
            }
        }
        toCall.toSortedMap(Comparator.comparingInt { it.getAnnotation(EventHandler::class.java).priority })
        for (entry in toCall) {
            try {
                entry.key(entry.value, event)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun isRegistered(listener: Any): Boolean {
        return listeners.contains(listener)
    }

    fun registerListener(listener: Any) {
        for (method in listener.javaClass.methods) {
            if (method.isAnnotationPresent(EventHandler::class.java) && method.parameterCount != 1) {
                throw IllegalArgumentException("Got a method ${method.name} with EventHandler annotation, but the parameter count is not 1 (${method.parameterCount})")
            }
        }
        listeners.add(listener)
    }

    fun unregisterListener(listener: Any) {
        listeners.remove(listener)
    }


}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class EventHandler(val priority: Int = 0)