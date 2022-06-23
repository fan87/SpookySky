import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import me.fan87.spookysky.buildsrc.BuildSrcCommon
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

var debugRegbex = false


repositories {
    maven {
        url = uri("https://github.com/Strezzed/strezz-central/raw/repository")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    maven {
        url = uri("https://libraries.minecraft.net/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://repository.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://mvnrepository.com/artifact")
    }

    mavenCentral()
}

dependencies {
    compileOnly("org.lwjgl.lwjgl:lwjgl:2.9.3")
    compileOnly("org.lwjgl.lwjgl:lwjgl_util:2.9.3")
    compileOnly("com.google.code.gson:gson:2.2.4")
    implementation("org.apache.logging.log4j:log4j-api:2.0-beta9")
    implementation("org.apache.logging.log4j:log4j-core:2.0-beta9")
}

allprojects {
    apply<BuildSrcCommon>()
    apply<KotlinPlatformJvmPlugin>()
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        if (debugRegbex) {
            implementation("me.fan87:regular-bytecode-expression:1.0.0-SNAPSHOT")
        } else {
            implementation("com.github.fan87:Regular-Bytecode-Expression:2.1.1")
        }
        implementation("org.ow2.asm:asm:9.3")
        implementation("org.ow2.asm:asm-util:9.3")
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.0")

        implementation("us.ihmc:euclid:0.17.2")
        implementation("us.ihmc:euclid-geometry:0.17.2")
        implementation("us.ihmc:euclid-frame:0.17.2")
        implementation("us.ihmc:euclid-shape:0.17.2")
        implementation("us.ihmc:euclid-frame-shape:0.17.2")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    register<JavaExec>("run") {
        dependsOn(":loader:classes")
        dependsOn("assemble")
    }
    register("agent") {
        dependsOn(":loader:classes")
        dependsOn(":loader:shadowJar")
        dependsOn("classes")
        dependsOn("shadowJar")
        val agentJar = File(project(":loader").buildDir, "libs/${project(":loader").name}-${project(":loader").version}-all.jar").absolutePath
        val clientJar = File(rootProject.buildDir, "libs/${rootProject.name}-${rootProject.version}-all.jar").absolutePath
        doFirst {
            println("Agent Jar: $agentJar")
            println("Client Jar: $clientJar")
            println("JVM Argument: -javaagent:$agentJar=$clientJar")
        }
    }
    register<JavaExec>("lunar") {

        dependsOn(":loader:classes")
        dependsOn(":loader:shadowJar")
        dependsOn("classes")
        dependsOn("shadowJar")
        val lunarHome = File(File(System.getProperty("user.home")), ".lunarclient")
        val jreHome = File(lunarHome, "jre/1.8/").listFiles { file -> file.isDirectory }!!.first()
        executable(File(jreHome, "bin/java"))
        main = "com.moonsworth.lunar.patcher.LunarMain"
        workingDir = File(lunarHome, "offline/1.8")
        val agentJar = File(project(":loader").buildDir, "libs/${project(":loader").name}-${project(":loader").version}-all.jar").absolutePath
        val clientJar = File(rootProject.buildDir, "libs/${rootProject.name}-${rootProject.version}-all.jar").absolutePath
        doFirst {
            try {
                Runtime.getRuntime().exec(arrayOf("killall", "-9", File(jreHome, "bin/java").absolutePath)).waitFor()
                Thread.sleep(100)
            } catch (_: Throwable) {}
        }
        jvmArgs = listOf(
            "--add-modules", "jdk.naming.dns",
            "--add-exports", "jdk.naming.dns/com.sun.jndi.dns=java.naming",
            "--add-opens", "java.base/java.io=ALL-UNNAMED",
            "--add-opens", "java.base/java.net=ALL-UNNAMED",
            "-Djna.boot.library.path=natives",
            "-Dlog4j2.formatMsgNoLookups=true",
            "-Djava.library.path=natives",
            "-javaagent:$agentJar=$clientJar",
            "-Xverify:all",
            "-agentlib:jdwp=transport=dt_socket,server=n,address=localhost:6950,suspend=y",
        )

        classpath = files(*workingDir.listFiles { _, name -> name?.endsWith(".jar") == true }!!)
        args = listOf(
            "--version", "1.8",
            "--accessToken", "0",
            "--assetIndex", "1.8",
            "--userProperties", "{}",
            "--gameDir", File(File(System.getProperty("user.home")), ".minecraft").absolutePath,
            "--texturesDir", File(lunarHome, "textures").absolutePath,
            "--launcherVersion", "2.9.1",
            "--hwid", "28fe925eeca05406329c39b8dbfafdba40117466843c5f874fae191b7080c499",
            "--width", "854",
            "--height", "480",
        )
    }
    register<JavaExec>("lunarRun") {

        dependsOn(":loader:classes")
        dependsOn(":loader:shadowJar")
        dependsOn("classes")
        dependsOn("shadowJar")
        val lunarHome = File(File(System.getProperty("user.home")), ".lunarclient")
        val jreHome = File(lunarHome, "jre/1.8/").listFiles { file -> file.isDirectory }!!.first()
        executable(File(jreHome, "bin/java"))
        main = "com.moonsworth.lunar.patcher.LunarMain"
        workingDir = File(lunarHome, "offline/1.8")
        val agentJar = File(project(":loader").buildDir, "libs/${project(":loader").name}-${project(":loader").version}-all.jar").absolutePath
        val clientJar = File(rootProject.buildDir, "libs/${rootProject.name}-${rootProject.version}-all.jar").absolutePath
        doFirst {
            try {
                Runtime.getRuntime().exec(arrayOf("killall", "-9", File(jreHome, "bin/java").absolutePath)).waitFor()
                Thread.sleep(100)
            } catch (_: Throwable) {}
        }
        jvmArgs = listOf(
            "--add-modules", "jdk.naming.dns",
            "--add-exports", "jdk.naming.dns/com.sun.jndi.dns=java.naming",
            "--add-opens", "java.base/java.io=ALL-UNNAMED",
            "--add-opens", "java.base/java.net=ALL-UNNAMED",
            "-Djna.boot.library.path=natives",
            "-Dlog4j2.formatMsgNoLookups=true",
            "-Djava.library.path=natives",
            "-javaagent:$agentJar=$clientJar",
            "-Xverify:all",
        )

        classpath = files(*workingDir.listFiles { _, name -> name?.endsWith(".jar") == true }!!)
        args = listOf(
            "--version", "1.8",
            "--accessToken", "0",
            "--assetIndex", "1.8",
            "--userProperties", "{}",
            "--gameDir", File(File(System.getProperty("user.home")), ".minecraft").absolutePath,
            "--texturesDir", File(lunarHome, "textures").absolutePath,
            "--launcherVersion", "2.9.1",
            "--hwid", "28fe925eeca05406329c39b8dbfafdba40117466843c5f874fae191b7080c499",
            "--width", "854",
            "--height", "480",
        )
    }

}
