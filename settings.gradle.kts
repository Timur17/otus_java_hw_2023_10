rootProject.name = "otusJava"

pluginManagement {
    val dependencyManagement: String by settings
    val johnrengelmanShadow: String by settings
    val sonarlint: String by settings
    val spotless: String by settings
    val springframeworkBoot: String by settings

    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
        id("org.springframework.boot") version springframeworkBoot
    }
}
include("hw01-gradle")
include("hw02")
include("hw04-generics")
include("hw06-annotations")
include("hw08-GC")
include("hw10-byteCode")
