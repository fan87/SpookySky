package me.fan87.spookysky.client.mapping.impl


import me.fan87.spookysky.client.mapping.ClassMapping
import me.fan87.spookysky.client.mapping.FieldMapping
import me.fan87.spookysky.client.mapping.WrapperClass

object MapTimer : ClassMapping<Timer>() {
    override fun getWrapperClass(): Class<Timer> {
        return Timer::class.java
    }

    override val humanReadableName: String
        get() = "Timer"

    val mapElapsedPartialTicks = FieldMapping<Float, Timer>(this, "elapsedPartialTicks")
    val mapTimerSpeed = FieldMapping<Float, Timer>(this, "timerSpeed")
    val mapTicksPerSecond = FieldMapping<Float, Timer>(this, "ticksPerSecond")
    val mapRenderPartialTicks = FieldMapping<Float, Timer>(this, "renderPartialTicks")

}

open class Timer protected constructor(original: Any) : WrapperClass(original) {
    var elapsedPartialTicks by MapTimer.mapElapsedPartialTicks
    var timerSpeed by MapTimer.mapTimerSpeed
    var ticksPerSecond by MapTimer.mapTicksPerSecond
    var renderPartialTicks by MapTimer.mapRenderPartialTicks
}
