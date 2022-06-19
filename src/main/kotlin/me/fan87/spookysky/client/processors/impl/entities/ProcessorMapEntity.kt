package me.fan87.spookysky.client.processors.impl.entities

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.MapMinecraft
import me.fan87.spookysky.client.mapping.impl.entities.Entity
import me.fan87.spookysky.client.mapping.impl.entities.MapEntity
import me.fan87.spookysky.client.mapping.impl.entities.MapEntityPlayerSP
import me.fan87.spookysky.client.processors.Processor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode

class ProcessorMapEntity: Processor("Map Entity") {


    val pattern = RegbexPattern {
        thenLdcStringEqual("Pos")
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("posX") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("posY") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("posZ") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenLdcStringEqual("Motion")
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("motionX") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("motionY") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("motionZ") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenLdcStringEqual("Rotation")
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("rotationYaw") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("rotationPitch") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenLdcStringEqual("FallDistance")
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("fallDistance") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenLdcStringEqual("OnGround")
        thenLazyAnyAmountOf { thenAny() }
        thenGroup("onGround") {
            thenOpcodeCheck(Opcodes.GETFIELD)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenLdcStringEqual("UUIDMost")
        thenLazyAnyAmountOf { thenAny() }
        thenThis()
        thenGroup("getUniqueID") {
            thenOpcodeCheck(Opcodes.INVOKEVIRTUAL)
        }
        thenLazyAnyAmountOf { thenAny() }
        thenLdcStringEqual("Saving entity NBT")
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapEntity.map(clazz.node.name)
                MapEntity.mapPosX.map(matcher.group("posX")!![0] as FieldInsnNode)
                MapEntity.mapPosY.map(matcher.group("posY")!![0] as FieldInsnNode)
                MapEntity.mapPosZ.map(matcher.group("posZ")!![0] as FieldInsnNode)
                MapEntity.mapMotionX.map(matcher.group("motionX")!![0] as FieldInsnNode)
                MapEntity.mapMotionY.map(matcher.group("motionY")!![0] as FieldInsnNode)
                MapEntity.mapMotionZ.map(matcher.group("motionZ")!![0] as FieldInsnNode)
                MapEntity.mapRotationYaw.map(matcher.group("rotationYaw")!![0] as FieldInsnNode)
                MapEntity.mapRotationPitch.map(matcher.group("rotationPitch")!![0] as FieldInsnNode)
                MapEntity.mapFallDistance.map(matcher.group("fallDistance")!![0] as FieldInsnNode)
                MapEntity.mapOnGround.map(matcher.group("onGround")!![0] as FieldInsnNode)
                MapEntity.mapGetUniqueID.map(matcher.group("getUniqueID")!![0] as MethodInsnNode)
                assertMapped(MapEntity)
                assertMapped(MapEntity.mapPosX)
                assertMapped(MapEntity.mapPosY)
                assertMapped(MapEntity.mapPosZ)
                assertMapped(MapEntity.mapMotionX)
                assertMapped(MapEntity.mapMotionY)
                assertMapped(MapEntity.mapMotionZ)
                assertMapped(MapEntity.mapRotationYaw)
                assertMapped(MapEntity.mapRotationPitch)
                assertMapped(MapEntity.mapFallDistance)
                assertMapped(MapEntity.mapOnGround)
                assertMapped(MapEntity.mapGetUniqueID)
                height(clazz.node)
            }
        }
        return false
    }

    fun height(clazz: ClassNode) {
        val pattern = RegbexPattern {
            thenThis()
            thenGroup("height") {
                thenOpcodeCheck(Opcodes.GETFIELD)
            }
            thenOpcodeCheck(Opcodes.LDC)
            thenOpcodeCheck(Opcodes.FMUL)
            thenReturn()
        }
        for (method in clazz.methods) {
            val matcher = pattern.matcher(method)
            if (matcher.next()) {
                MapEntity.mapGetEyeHeight.map(method)
                MapEntity.mapHeight.map(matcher.group("height")!![0] as FieldInsnNode)
            }
        }
        assertMapped(MapEntity.mapGetEyeHeight)
        assertMapped(MapEntity.mapHeight)
    }

    override fun jobDone(): Boolean {
        return MapMinecraft.mapThePlayer.isMapped()
    }
}