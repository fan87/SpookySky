package me.fan87.spookysky.client.processors.impl.render

import me.fan87.regbex.RegbexPattern
import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.rendering.MapRender
import me.fan87.spookysky.client.mapping.impl.rendering.MapRenderManager
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.CaptureUtils.groupAsFieldInsnNode
import org.objectweb.asm.Opcodes
import org.objectweb.asm.signature.SignatureReader
import java.util.regex.Pattern

class ProcessorMapRender: Processor("Map Render") {

    init {
        dependsOn(MapRenderManager)
    }

    override fun start() {
        onlyProcess(MapRenderManager)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            if (method.name == "<init>") {
                val pattern = RegbexPattern {
                    thenThis()
                    thenGroup("entityRenderMap") {
                        thenOpcodeCheck(Opcodes.GETFIELD)
                    }
                    thenOpcodeCheck(Opcodes.LDC)
                    thenOpcodeCheck(Opcodes.NEW)
                    thenOpcodeCheck(Opcodes.DUP)
                    thenThis()
                    thenOpcodeCheck(Opcodes.INVOKESPECIAL)
                    thenOpcodeCheck(Opcodes.INVOKEINTERFACE)
                    thenOpcodeCheck(Opcodes.POP)
                }
                val matcher = pattern.matcher(method)
                matcher.next()
                val field = matcher.groupAsFieldInsnNode("entityRenderMap")
                val entityRenderMap =
                    clazz.node.fields.first { it.name == field.name && it.desc == field.desc }
                println(entityRenderMap.signature)
                //Ljava/util/Map<Ljava/lang/Class;Lnet/minecraft/v1_8/speaaspssphpesspsppeheepa;>;
                val regex = Pattern.compile("Ljava\\/util\\/Map<Ljava\\/lang\\/Class(?:<.*?>)?;L([\\w\\d_\\/]*)(?:<.*?>)?;>")
                val signatureMatcher = regex.matcher(entityRenderMap.signature)
                signatureMatcher.find(0)
                MapRender.map(signatureMatcher.group(1))
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapRender.isMapped()
    }
}