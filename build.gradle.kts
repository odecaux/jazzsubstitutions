plugins {
    kotlin("jvm") version "1.3.61"
}

allprojects {
    group = "com.odecaux"
    version = "1.0-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}