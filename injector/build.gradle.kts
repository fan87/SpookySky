plugins {
    id("java")
    id("c")
}

group = "me.fan87"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mageddo.jvmattach:jvm-attach:1.0.0");
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks {
    create("injector-linux") {
        dependsOn("classes")
        doFirst {
            exec {
                var classpath = project.configurations.compileClasspath.map { it.files }.let {
                    val out = ArrayList<File>()
                    for (file in it.get()) {
                        out.add(file)
                    }
                    out.toTypedArray()
                }.joinToString(":")
                if (classpath.length > 0) {
                    classpath = ":" + classpath
                }
                println("Classpath: $classpath")
                commandLine("bash", "-c", "echo \"${System.getProperty("user.password")}\" | sudo -S " +
                        "${System.getProperty("java.home")}/bin/java " +
                        "-Djava.library.path=/home/fan87/Desktop/spookynative/ " +
                        "-cp ${project.buildDir.absolutePath}/classes/java/main$classpath me.fan87.spookysky.injector.Main")
            }
        }
    }

}