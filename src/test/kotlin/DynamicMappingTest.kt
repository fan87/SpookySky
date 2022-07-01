import me.fan87.spookysky.client.utils.ASMUtils
import me.fan87.spookysky.mapper.SpookyMinecraftMapper
import org.junit.jupiter.api.Test
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.io.OutputStream
import java.io.PrintStream
import java.io.PrintWriter
import java.util.jar.JarFile
import java.util.regex.Pattern
import kotlin.system.exitProcess
import kotlin.test.assertTrue

internal class DynamicMappingTest {

    @Test
    internal fun dynamicMapTest() {
        map(File("client.jar"))
    }

    /**
     * No argument check
     */
    fun map(jarFile: File) {
        val version = jarFile.name.substring(0, jarFile.name.length - 4)
        val jar = JarFile(jarFile)
        val classes = ArrayList<ClassNode>()
        for (entry in jar.entries()) {
            if (entry.name.endsWith(".class")) {
                try {
                    jar.getInputStream(entry).use {
                        classes.add(ASMUtils.parseClass(it.readBytes()))
                    }
                } catch (_: IllegalArgumentException) {
                    System.err.println("[-] [${javaClass.simpleName}] Unable to parse class: ${entry.name}, ignoring it")
                }
            }
        }
        val oldOut = System.out
        System.setOut(PrintStream(object : OutputStream() { override fun write(b: Int) {} }))
        val mapper = SpookyMinecraftMapper(classes)
        mapper.processorsManager.onError = {
            mapper.stop()
            System.setOut(oldOut)
            println(" ===== Map Result of $version ===== ")
            mapper.dumpToConsole()
            println(" ================================== ")
            throw AssertionError("Failed to map version: $version")
        }
        val startTime = System.currentTimeMillis()
        var lastSendTime = System.currentTimeMillis()
        while (true) {
            if (System.currentTimeMillis() - lastSendTime > 1000) {
                println("[!] [${javaClass.simpleName}] Total Mappings: ${mapper.mappingsManager.allMappings.filter { it.isMapped() }.size}/${mapper.mappingsManager.allMappings.size}")
                lastSendTime = System.currentTimeMillis()
            }
            if (System.currentTimeMillis() - startTime > timeout) {
                mapper.stop()
                System.setOut(oldOut)
                println(" ===== Map Result of $version ===== ")
                mapper.dumpToConsole()
                println(" ================================== ")
                throw AssertionError("Failed to map version: $version")
            }
            if (mapper.isFinished()) {
                break
            }
        }
        mapper.stop()
        System.setOut(oldOut)
        println(" ===== Map Result of $version ===== ")
        mapper.dumpToConsole()
        println(" ================================== ")
        mapper.close()
    }



}