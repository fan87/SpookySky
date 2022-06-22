package me.fan87.spookysky.client.utils

class AnimationTimer(var total: Long, val easingFunction: (Float) -> Float = {it}) {

    var endTime = System.currentTimeMillis()

    fun getProgress(): Float {
        return easingFunction(1f - (((endTime - System.currentTimeMillis())*1000/total*1000).toFloat()/1000000f).coerceAtLeast(0.0f).coerceAtMost(1.0f))
    }
    fun getProgressReverted(): Float {
        return easingFunction((((endTime - System.currentTimeMillis())*1000/total*1000).toFloat()/1000000f).coerceAtLeast(0.0f).coerceAtMost(1.0f))
    }
    fun getValue(start: Double, end: Double): Double {
        return getProgress()*(end - start) + start
    }
    fun getValue(start: Int, end: Int): Int {
        return (getProgress()*(end - start)).toInt() + start
    }
    fun getValue(start: Long, end: Long): Long {
        return (getProgress()*(end - start)).toLong() + start
    }
    fun getValue(start: Float, end: Float): Float {
        return (getProgress()*(end - start)) + start
    }

    fun startAnimation() {
        endTime = System.currentTimeMillis() + total
    }

    fun endAnimation() {
        endTime = System.currentTimeMillis()
    }

}