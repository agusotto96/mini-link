plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.aottolini"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:4.3.0")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("com.auth0:java-jwt:3.18.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("commons-validator:commons-validator:1.7")
}

application {
    mainClass.set("MainKt")
}
