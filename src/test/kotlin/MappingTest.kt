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

const val timeout = 20000
internal class MappingTest {

    companion object {
        const val minMinor = 8
        const val maxMinor = 8
    }


    val minecraftVersionsDirectory: File

    init {
        if ("win" in System.getProperty("os.name")) { // Windows Support
            minecraftVersionsDirectory = File(System.getProperty("user.home"), "AppData\\Roaming\\.minecraft\\versions")
        } else { // Linux, and hopefully MacOS Support
            minecraftVersionsDirectory = File(System.getProperty("user.home"), ".minecraft/versions")
        }
    }

    @Test
    internal fun mapTest() {
        println("Waiting 5 seconds so you can attach everything you may need for this run...")
        Thread.sleep(5000)
        println("Started! Assuming you have everything prepared.")
        assertTrue(minecraftVersionsDirectory.exists(), "Minecraft Version Directory doesn't exist!")
        val versions = arrayListOf(*(minMinor..maxMinor).toList().toTypedArray())
        for (dir in minecraftVersionsDirectory.listFiles()) {
            if (!shouldAttempt(dir)) {
                continue
            }
            val name = dir.name.let { if (it.endsWith("/") || it.endsWith("\\")) it.substring(0, it.length - 1) else it }
            val jarFile = File(dir, "${name}.jar")
            if (!jarFile.exists() || !jarFile.isFile) {
                continue
            }
            val matcher = versionNumberPattern.matcher(name)
            matcher.find()
            val minor = matcher.group(2).toInt()
            versions.remove(minor)
            println("[!] [${javaClass.simpleName}] Attempting to map $name")
            map(jarFile)
            println("[+] [${javaClass.simpleName}] Successfully mapped $name")
        }
        if (versions.isNotEmpty()) {
            System.err.println(" ===== Untested Versions ===== ")
            System.err.println("// Versions below are supposed to be tested, but they are not found in your .minecraft/versions")
            for (version in versions) {
                System.err.println("1.$version.*")
            }
        }
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

    val versionNumberPattern = Pattern.compile("(\\d+)\\.(\\d+)(\\.(\\d+))?")

    fun shouldAttempt(file: File): Boolean {
        if (!file.isDirectory) {
            return false
        }
        val name = file.name.let { if (it.endsWith("/") || it.endsWith("\\")) it.substring(0, it.length - 1) else it }
        if (!name.matches(versionNumberPattern.toRegex())) {
            return false
        }
        val matcher = versionNumberPattern.matcher(name)
        matcher.find()
        val major = matcher.group(1).toInt()
        val minor = matcher.group(2).toInt()
        if (major != 1) {
            return false
        }
        if (minor < minMinor || minor > maxMinor) {
            return false
        }
        return true
    }

}