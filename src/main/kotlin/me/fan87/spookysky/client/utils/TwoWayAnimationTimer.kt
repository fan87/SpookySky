package me.fan87.spookysky.client.utils

class TwoWayAnimationTimer(var total: Long, val easingFunction: (Float) -> Float = {it}) {

    var endTime = System.currentTimeMillis()

    private fun getProgress0(): Float {
        return 1f - (((endTime - System.currentTimeMillis())*1000/total*1000).toFloat()/1000000f).coerceAtLeast(0.0f).coerceAtMost(1.0f)
    }
    private fun getProgressReverted0(): Float {
        return (((endTime - System.currentTimeMillis())*1000/total*1000).toFloat()/1000000f).coerceAtLeast(0.0f).coerceAtMost(1.0f)
    }
    fun getProgress(): Float {
        return easingFunction(if (isIn) {
            getProgressReverted0()
        } else {
            getProgress0()
        })
    }
    fun getProgressReverted(): Float {
        return easingFunction(if (isIn) {
            getProgress0()
        } else {
            getProgressReverted0()
        })
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

    private var isIn = false

    fun inAnimation() {
        if (!isIn) {
            isIn = true
            endTime = System.currentTimeMillis() + total
        }
    }

    fun outAnimation() {
        if (isIn) {
            isIn = false
            endTime = System.currentTimeMillis() + total
        }

    }

}