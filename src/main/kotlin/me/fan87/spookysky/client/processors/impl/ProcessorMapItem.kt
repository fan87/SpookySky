package me.fan87.spookysky.client.processors.impl

import me.fan87.spookysky.client.LoadedClass
import me.fan87.spookysky.client.mapping.impl.item.MapItem
import me.fan87.spookysky.client.mapping.impl.item.MapItemStack
import me.fan87.spookysky.client.processors.Processor
import me.fan87.spookysky.client.utils.ASMUtils
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.LdcInsnNode

class ProcessorMapItem: Processor("Map Item") {

    init {
        dependsOn(MapItem)

    }

    override fun start() {
        onlyProcess(MapItem)
    }

    override fun process(clazz: LoadedClass): Boolean {
        for (method in clazz.node.methods) {
            for (instruction in method.instructions) {
                if (instruction !is LdcInsnNode) {
                    continue
                }
                val cst = instruction.cst
                if (cst !is String) {
                    continue
                }

                if (cst == ".name") {
                    MapItem.mapGetItemStackDisplayName.map(method)
                    break
                }
                if (cst == "item.") {
                    if (method.desc == "()Ljava/lang/String;") {
                        MapItem.mapGetUnlocalizedName.map(method)
                        MapItem.mapUnlocalizedName.map(method.instructions.filterIsInstance(FieldInsnNode::class.java).first())
                    } else {
                        MapItem.mapGetUnlocalizedName_ItemStack.map(method)
                        MapItemStack.map(ASMUtils.descTypeToJvmType(ASMUtils.getParameterTypes(method.desc)[0]))
                    }
                    break
                }
            }
        }
        return false
    }

    override fun jobDone(): Boolean {
        return MapItem.mapGetItemStackDisplayName.isMapped()
    }
}