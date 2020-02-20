plugins {
    kotlin("jvm") version "1.3.61"
}

group = "org.cottand"
version = "1.0"

repositories {
    mavenCentral()
}

val arrowVer = "0.10.4"
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}