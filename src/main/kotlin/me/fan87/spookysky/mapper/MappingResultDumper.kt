package me.fan87.spookysky.mapper

import me.fan87.spookysky.client.mapping.*
import me.fan87.spookysky.client.processors.ProcessorsManager
import java.io.PrintWriter

const val reset = "\u001B[0m"
const val red = "\u001B[31m"
const val green = "\u001B[32m"

class MappingResultDumper(val mappingsManager: MappingsManager, val processorsManager: ProcessorsManager) {

    fun dumpMapping(output: PrintWriter, ansi: Boolean, mapping: Mapping<*>) {
        if (mapping.isMapped()) {
            if (ansi) {
                output.print(green)
            } else {
                output.print("[+] ")
            }
        } else {
            if (ansi) {
                output.print(red)
            } else {
                output.print("[-] ")
            }
        }
        output.print(mapping.humanReadableName)
        if (mapping.isMapped()) {
            output.print("  -  ${mapping.mapped}")
        }
        output.print("\n")
    }

    fun dump(output: PrintWriter, ansi: Boolean) {
        output.println("Class Mappings: ${mappingsManager.mappings.filter { it.isMapped() }.size}/${mappingsManager.mappings.size}")
        output.println("Field Mappings: ${mappingsManager.allMappings.filterIsInstance<FieldMapping<*, *>>().filter { it.isMapped() }.size}/${mappingsManager.allMappings.filterIsInstance<FieldMapping<*, *>>().size}")
        output.println("Method Mappings: ${mappingsManager.allMappings.filterIsInstance<MethodMapping<*, *>>().filter { it.isMapped() }.size}/${mappingsManager.allMappings.filterIsInstance<MethodMapping<*, *>>().size}")
        output.println("Member Mappings: ${mappingsManager.allMappings.filterIsInstance<MemberMapping<*>>().filter { it.isMapped() }.size}/${mappingsManager.allMappings.filterIsInstance<MemberMapping<*>>().size}")
        output.println("Total Mappings: ${mappingsManager.allMappings.filter { it.isMapped() }.size}/${mappingsManager.allMappings.size}")
        output.println()
        output.println(" - Mappings")
        for (mapping in mappingsManager.mappings) {
            dumpMapping(output, ansi, mapping)
            for (child in mapping.children) {
                output.print("\t")
                dumpMapping(output, ansi, child)
            }
        }
        output.println()
        output.println(" - Processors")
        for (processor in processorsManager.processors) {
            if (!processor.jobDone()) {
                if (ansi) {
                    output.print(red)
                }
            } else {
                if (ansi) {
                    output.print(green)
                }
            }
            output.print(processor.humanReadableName)
            if (!processor.jobDone()) {
                if (!processor.canProcess()) {
                    output.print(" - Couldn't start process")
                    for (dependency in processor.dependencies) {
                        output.print("\n\tDepends on: $dependency")
                    }
                    for (dependency in processor.onlyProcessMappings) {
                        output.print("\n\tOnly Process: $dependency")
                    }
                }
            }
            output.print("\n")
        }
        output.print(reset)
        output.flush()
    }

}