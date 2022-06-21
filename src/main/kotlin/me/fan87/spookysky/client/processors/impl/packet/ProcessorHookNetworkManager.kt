package me.fan87.spookysky.client.processors.impl.packet

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.packets.MapNetworkManager
import me.fan87.spookysky.client.mapping.impl.packets.PacketEventHandler
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils.addGetField
import me.fan87.spookysky.client.utils.ASMUtils.addMethodCall
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.VarInsnNode
import java.io.PrintStream

class ProcessorHookNetworkManager: Processor("Hook NetworkManager") {

    init {
        dependsOn(MapNetworkManager.mapSendPacket)
        dependsOn(MapNetworkManager.mapChannelRead0)
        dependsOn(MapNetworkManager)
    }

    override fun start() {
        onlyProcess(MapNetworkManager)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (MapNetworkManager.mapSendPacket.isSame(method)) {
                val out = InsnList()
                val end = LabelNode()
                out.add(VarInsnNode(Opcodes.ALOAD, 1))
                out.addMethodCall(PacketEventHandler::onPacketSent)
                out.add(JumpInsnNode(Opcodes.IFEQ, end))
                out.add(InsnNode(Opcodes.RETURN))
                out.add(end)
                out.add(method.instructions)
                method.instructions = out
            }
            if (MapNetworkManager.mapChannelRead0.isSame(method)) {
                val out = InsnList()
                val end = LabelNode()
                out.add(VarInsnNode(Opcodes.ALOAD, 2))
                out.addMethodCall(PacketEventHandler::onPacketReceive)
                out.add(JumpInsnNode(Opcodes.IFEQ, end))
                out.add(InsnNode(Opcodes.RETURN))
                out.add(end)
                out.add(method.instructions)
                method.instructions = out
            }
        }
        done = true
        return true
    }
    var done = false

    override fun jobDone(): Boolean {
        return done
    }
}