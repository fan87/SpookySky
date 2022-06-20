package me.fan87.spookysky.client.mapping.impl.settings


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.MethodMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapKeyBinding : ClassMapping<KeyBinding>() {
    override fun getWrapperClass(): Class<KeyBinding> {
        return KeyBinding::class.java
    }

    override val humanReadableName: String
        get() = "KeyBinding"

    val mapOnTick = MethodMapping<Unit, KeyBinding>(this, "onTick(int)")
    val mapSetKeyBindState = MethodMapping<Unit, KeyBinding>(this, "setKeyBindState(int, boolean)")

    val mapKeyDescription = FieldMapping<String, KeyBinding>(this, "keyDescription")
    val mapKeyCode = FieldMapping<Int, KeyBinding>(this, "keyCode")
    val mapKeyCodeDefault = FieldMapping<Int, KeyBinding>(this, "keyCodeDefault")
    val mapKeyCategory = FieldMapping<String, KeyBinding>(this, "keyCategory")
    val mapPressed = FieldMapping<Boolean, KeyBinding>(this, "pressed")
    val mapPressTime = FieldMapping<Int, KeyBinding>(this, "pressTime")

}

open class KeyBinding(original: Any) : WrapperClass(original) {

    fun isPressed(): Boolean {
        return if (pressTime == 0) {
            false
        } else {
            --pressTime
            true
        }
    }
    fun unpresskey() {
        pressTime = 0
        pressed = false
    }

    val keyDescription by MapKeyBinding.mapKeyDescription
    val keyCode by MapKeyBinding.mapKeyCode
    val keyCodeDefault by MapKeyBinding.mapKeyCodeDefault
    val keyCategory by MapKeyBinding.mapKeyCategory
    var pressed by MapKeyBinding.mapPressed
    var pressTime by MapKeyBinding.mapPressTime

}
