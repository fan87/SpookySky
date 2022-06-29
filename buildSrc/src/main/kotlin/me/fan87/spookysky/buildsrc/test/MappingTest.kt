package me.fan87.spookysky.buildsrc.test

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class MappingTest: DefaultTask() {

    @get:Input
    abstract val minecraftVersionsDirectory: DirectoryProperty

    init {
        if ("win" in System.getProperty("os.name")) { // Windows Support
            minecraftVersionsDirectory.convention(project.layout.projectDirectory.dir(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft\\versions"))
        } else { // Linux, and hopefully MacOS Support
            minecraftVersionsDirectory.convention(project.layout.projectDirectory.dir(System.getProperty("user.home") + "/.minecraft/versions"))
        }
    }

    @TaskAction
    fun run() {
        println(minecraftVersionsDirectory.asFile.get().absoluteFile)
    }


}