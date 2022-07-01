package me.fan87.spookysky.client.utils

import me.fan87.regbex.RegbexMatcher
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.TypeInsnNode

object CaptureUtils {

    fun RegbexMatcher.groupAsTypeInsnNode(groupName: String): TypeInsnNode {
        return group(groupName)!![0] as TypeInsnNode
    }
    fun RegbexMatcher.groupAsMethodInsnNode(groupName: String): MethodInsnNode {
        return group(groupName)!![0] as MethodInsnNode
    }
    fun RegbexMatcher.groupAsLdcStringCst(groupName: String): String {
        return (group(groupName)!![0] as LdcInsnNode).cst as String
    }
    fun RegbexMatcher.groupAsLdcCst(groupName: String): Any {
        return (group(groupName)!![0] as LdcInsnNode).cst
    }
    fun RegbexMatcher.groupAsFieldInsnNode(groupName: String): FieldInsnNode {
        return group(groupName)!![0] as FieldInsnNode
    }
    fun RegbexMatcher.groupAsJumpInsnNode(groupName: String): JumpInsnNode {
        return group(groupName)!![0] as JumpInsnNode
    }

}