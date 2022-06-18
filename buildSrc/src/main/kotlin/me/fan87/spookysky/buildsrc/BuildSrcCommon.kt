package me.fan87.spookysky.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildSrcCommon: Plugin<Project> {
    override fun apply(target: Project) {
        target.version = "1.0-SNAPSHOT"
        target.group = "me.fan87"
    }
}