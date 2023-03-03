import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    application
}

// Configuración de klint
ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}

// var moshiVersion = "1.11.0"
var moshiVersion = "1.14.0"
// var kotestVersion = "4.4.3"
var kotestVersion = "5.5.4"
var okHttpVersion = "4.10.0"
var mockKVersion = "1.13.4"

group = "es.edu.iesra.daw.revilofe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

//    implementation("org.jetbrains.kotlin:kotlin-stdlib")
//    implementation("org.jetbrains.kotlin:kotlin-script-runtime")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    implementation("org.slf4j:slf4j-simple:1.7.25")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-extensions-junitxml-jvm:$kotestVersion")
    testImplementation("io.mockk:mockk:$mockKVersion")
}

tasks.test {
    useJUnit()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
