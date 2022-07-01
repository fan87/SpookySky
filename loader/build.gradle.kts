import com.github.jengelman.gradle.plugins.shadow.relocation.Relocator
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import me.fan87.spookysky.buildsrc.BuildSrcCommon

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}


group = "me.fan87"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(rootProject)
}

tasks {
    jar {
        manifest {
            attributes["Premain-Class"] = "me.fan87.spookysky.loader.Main"
            attributes["Can-Redefine-Classes"] = true
            attributes["Can-Retransform-Classes"] = true
        }
    }

    shadowJar {
        dependsOn(rootProject.tasks.findByName("shadowJar"))
        relocate("me.fan87.spookysky.client", "me.fan87.spookysky.loader.integrated.client")
        relocate("org.objectweb.asm", "me.fan87.spookysky.org.objectweb.asm")
        from(rootProject.buildDir.absolutePath + "/libs/${rootProject.name}-${rootProject.version}-all.jar")
    }

}
