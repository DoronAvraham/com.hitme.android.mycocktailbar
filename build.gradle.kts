buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.DAGGER_HILT}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.KTLINT}")
        classpath("com.github.ben-manes:gradle-versions-plugin:${Versions.DEPENDENCY_UPDATER}")
    }
}

plugins {
    id(Plugins.APPLICATION) version Versions.AGP apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN apply false
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.KOTLIN apply false
    id("com.github.ben-manes.versions") version Versions.DEPENDENCY_UPDATER apply false
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    repositories {
        google()
        mavenCentral()
    }
}
