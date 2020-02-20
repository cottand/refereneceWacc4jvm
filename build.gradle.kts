plugins {
  kotlin("jvm") version "1.3.61"
  id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "org.cottand"
version = "1.0"

repositories {
  jcenter()
}

val fuelVer = "2.2.1"
dependencies {
  implementation(kotlin("stdlib-jdk8"))

  // Simple HTTP client, Fuel
  implementation("com.github.kittinunf.fuel:fuel:$fuelVer")
  implementation("com.github.kittinunf.fuel:fuel-gson:$fuelVer")

  // JSON serialisation library, Gson
  implementation("com.google.code.gson:gson:2.8.5")

}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}