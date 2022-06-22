package me.fan87.spookysky.client.utils

import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.entities.EntityLivingBase
import kotlin.math.*


object MathUtils {

    fun getRotationVector3(yaw: Float, pitch: Float): Vector3f {
        val rYaw = Math.toRadians(yaw.toDouble())
        val rPitch = Math.toRadians(pitch.toDouble())
        val z: Double = cos(rYaw) * cos(rPitch)
        val y: Double = -sin(rPitch)
        val x: Double = -sin(rYaw) * cos(rPitch)
        return Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
    }
    fun getRotationVector2(yaw: Float): Vector2f {
        val rYaw = Math.toRadians(yaw.toDouble())
        val y: Double = cos(rYaw)
        val x: Double = -sin(rYaw)
        return Vector2f(x.toFloat(), y.toFloat())
    }

    fun Entity.tryFace(x: Double, y: Double, z: Double): Vector2f {
        var actualFacingX = x
        var actualFacingY = y
        var actualFacingZ = z
        actualFacingX = this.posX - actualFacingX
        actualFacingY = this.posY + this.getEyeHeight().toDouble() - actualFacingY
        actualFacingZ = this.posZ - actualFacingZ
        val yaw = (Math.toDegrees(atan2(actualFacingZ, actualFacingX)) + 90).toFloat()
        val pitch = Math.toDegrees(
            atan(actualFacingY / Vector2d(actualFacingX, actualFacingZ).delta())
        ).toFloat()
        return Vector2f(yaw, pitch)
    }
    fun tryFace(viewerPos: Vector3d, position: Vector3d): Vector2f {
        var actualFacingX = position.x
        var actualFacingY = position.y
        var actualFacingZ = position.z
        actualFacingX = viewerPos.x - actualFacingX
        actualFacingY = viewerPos.y - actualFacingY
        actualFacingZ = viewerPos.z - actualFacingZ
        val yaw = (Math.toDegrees(atan2(actualFacingZ, actualFacingX)) + 90).toFloat()
        val pitch = Math.toDegrees(
            atan(actualFacingY / Vector2d(actualFacingX, actualFacingZ).delta())
        ).toFloat()
        return Vector2f(yaw, pitch)
    }

    fun Entity.addMotion(motion: Vector3d) {
        motionX += motion.x
        motionY += motion.y
        motionZ += motion.z
    }
    fun Entity.addMotion(motion: Vector2d) {
        motionX += motion.x
        motionZ += motion.y
    }
    fun Entity.setMotion(motion: Vector3d) {
        motionX = motion.x
        motionY = motion.y
        motionZ = motion.z
    }
    fun Entity.setMotion(motion: Vector2d) {
        motionX = motion.x
        motionZ = motion.y
    }
    fun Entity.addMotion(motion: Vector3f) {
        motionX += motion.x.toDouble()
        motionY += motion.y.toDouble()
        motionZ += motion.z.toDouble()
    }
    fun Entity.addMotion(motion: Vector2f) {
        motionX += motion.x.toDouble()
        motionZ += motion.y.toDouble()
    }
    fun Entity.setMotion(motion: Vector3f) {
        motionX = motion.x.toDouble()
        motionY = motion.y.toDouble()
        motionZ = motion.z.toDouble()
    }
    fun Entity.setMotion(motion: Vector2f) {
        motionX = motion.x.toDouble()
        motionZ = motion.y.toDouble()
    }
    fun Entity.getMotion(): Vector3d {
        return Vector3d(motionX, motionY, motionZ)
    }



    fun Entity.getPosition(): Vector3d {
        return Vector3d(posX, posY, posZ)
    }
    fun Entity.addPosition(position: Vector3d) {
        posX += position.x
        posY += position.y
        posZ += position.z
    }
    fun Entity.addPosition(position: Vector2d) {
        posX += position.x
        posZ += position.y
    }
    fun Entity.setPosition(position: Vector3d) {
        posX = position.x
        posY = position.y
        posZ = position.z
    }
    fun Entity.setPosition(position: Vector2d) {
        posX = position.x
        posZ = position.y
    }

    fun Entity.distanceTo(another: Vector3d): Double {
        return (another - getPosition()).delta()
    }
    fun Entity.getDistanceTo(another: Vector3d): Double {
        return (another - getPosition()).delta()
    }
    fun Entity.getDistanceToEntity(another: Entity): Double {
        return (another.getPosition() - getPosition()).delta()
    }

    fun Entity.getRotatedVector3(): Vector3f {
        return getRotationVector3(rotationYaw, rotationPitch)
    }

    fun Entity.getRotatedVector2(): Vector2f {
        return getRotationVector2(rotationYaw)
    }

    fun EntityLivingBase.getDirection(): Float {
        var rotationYaw: Float = this.rotationYaw
        return Math.toDegrees(atan2(-this.moveStrafing, this.moveForward).toDouble()).toFloat() + rotationYaw
    }

}


data class Vector3f(val x: Float, val y: Float, val z: Float) {
    operator fun times(amount: Vector3f): Vector3f {
        return Vector3f(x * amount.x, y * amount.y, z * amount.z)
    }
    operator fun times(amount: Float): Vector3f {
        return Vector3f(x * amount, y * amount, z * amount)
    }
    operator fun plus(amount: Vector3f): Vector3f {
        return Vector3f(x + amount.x, y + amount.y, z + amount.z)
    }
    operator fun plus(amount: Float): Vector3f {
        return Vector3f(x + amount, y + amount, z + amount)
    }
    operator fun div(amount: Vector3f): Vector3f {
        return Vector3f(x / amount.x, y / amount.y, z / amount.z)
    }
    operator fun div(amount: Float): Vector3f {
        return Vector3f(x / amount, y / amount, z / amount)
    }
    operator fun minus(amount: Vector3f): Vector3f {
        return Vector3f(x - amount.x, y - amount.y, z - amount.z)
    }
    operator fun minus(amount: Float): Vector3f {
        return Vector3f(x - amount, y - amount, z - amount)
    }
    fun delta(): Float {
        return sqrt(x * x + y * y + z * z)
    }

    fun toVector3d(): Vector3d {
        return Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
    }
}

data class Vector2f(val x: Float, val y: Float) {
    operator fun times(amount: Vector2f): Vector2f {
        return Vector2f(x * amount.x, y * amount.y)
    }
    operator fun times(amount: Float): Vector2f {
        return Vector2f(x * amount, y * amount)
    }
    operator fun plus(amount: Vector2f): Vector2f {
        return Vector2f(x + amount.x, y + amount.y)
    }
    operator fun plus(amount: Float): Vector2f {
        return Vector2f(x + amount, y + amount)
    }
    operator fun div(amount: Vector2f): Vector2f {
        return Vector2f(x / amount.x, y / amount.y)
    }
    operator fun div(amount: Float): Vector2f {
        return Vector2f(x / amount, y / amount)
    }
    operator fun minus(amount: Vector2f): Vector2f {
        return Vector2f(x - amount.x, y - amount.y)
    }
    operator fun minus(amount: Float): Vector2f {
        return Vector2f(x - amount, y - amount)
    }
    fun delta(): Float {
        return sqrt(x * x + y * y)
    }

    fun toVector2d(): Vector2d {
        return Vector2d(x.toDouble(), y.toDouble())
    }

    fun toVector3f(): Vector3f {
        return Vector3f(x, 0f, y)
    }
}

data class Vector3d(val x: Double, val y: Double, val z: Double) {
    operator fun times(amount: Vector3d): Vector3d {
        return Vector3d(x * amount.x, y * amount.y, z * amount.z)
    }
    operator fun times(amount: Double): Vector3d {
        return Vector3d(x * amount, y * amount, z * amount)
    }
    operator fun plus(amount: Vector3d): Vector3d {
        return Vector3d(x + amount.x, y + amount.y, z + amount.z)
    }
    operator fun plus(amount: Double): Vector3d {
        return Vector3d(x + amount, y + amount, z + amount)
    }
    operator fun div(amount: Vector3d): Vector3d {
        return Vector3d(x / amount.x, y / amount.y, z / amount.z)
    }
    operator fun div(amount: Double): Vector3d {
        return Vector3d(x / amount, y / amount, z / amount)
    }
    operator fun minus(amount: Vector3d): Vector3d {
        return Vector3d(x - amount.x, y - amount.y, z - amount.z)
    }
    operator fun minus(amount: Double): Vector3d {
        return Vector3d(x - amount, y - amount, z - amount)
    }
    fun delta(): Double {
        return sqrt(x * x + y * y + z * z)
    }

    fun toVector3f(): Vector3f {
        return Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
    }
}

data class Vector2d(val x: Double, val y: Double) {
    operator fun times(amount: Vector2d): Vector2d {
        return Vector2d(x * amount.x, y * amount.y)
    }
    operator fun times(amount: Double): Vector2d {
        return Vector2d(x * amount, y * amount)
    }
    operator fun plus(amount: Vector2d): Vector2d {
        return Vector2d(x + amount.x, y + amount.y)
    }
    operator fun plus(amount: Double): Vector2d {
        return Vector2d(x + amount, y + amount)
    }
    operator fun div(amount: Vector2d): Vector2d {
        return Vector2d(x / amount.x, y / amount.y)
    }
    operator fun div(amount: Double): Vector2d {
        return Vector2d(x / amount, y / amount)
    }
    operator fun minus(amount: Vector2d): Vector2d {
        return Vector2d(x - amount.x, y - amount.y)
    }
    operator fun minus(amount: Double): Vector2d {
        return Vector2d(x - amount, y - amount)
    }
    fun delta(): Double {
        return sqrt(x * x + y * y)
    }

    fun toVector2f(): Vector2f {
        return Vector2f(x.toFloat(), y.toFloat())
    }

    fun toVector3d(): Vector3d {
        return Vector3d(x, 0.0, y)
    }
}
