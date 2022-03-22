buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.DAGGER_HILT}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
    }
}

plugins {
    id("com.android.application") version Versions.PLUGIN_APPLICATION apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN apply false
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.KOTLIN apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}