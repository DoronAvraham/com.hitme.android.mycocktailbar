object AndroidVersions {
    const val MIN_SDK = 23
    const val TARGET_SDK = 33
    const val COMPILE_SDK = 33
}

object Versions {
    // Android dependencies
    const val AGP = "7.3.1"
    const val ANDROIDX_APPCOMPAT = "1.4.1"
    const val ANDROIDX_CORE_KTX = "1.7.0"
    const val ANDROIDX_DATASTORE = "1.0.0"
    const val ANDROIDX_HILT = "1.0.0"
    const val ANDROIDX_NAVIGATION = "2.5.3"
    const val ANDROIDX_LIFECYCLE = "2.5.1"
    const val ANDROIDX_ROOM = "2.4.2"
    const val COMPOSE = "1.3.2"
    const val COMPOSE_ACTIVITY = "1.6.1"
    const val COMPOSE_MATERIAL = "1.3.1"
    const val DAGGER_HILT = "2.44.2"

    // 3rd party dependencies
    const val COIL = "2.2.2"
    const val DEPENDENCY_UPDATER = "0.42.0"
    const val KOTLIN = "1.7.20"
    const val KOTLINX_COROUTINES = "1.6.4"
    const val KOTLINX_SERIALIZATION = "1.4.1"
    const val KTLINT = "10.2.1"
    const val KTOR = "2.0.2"
    const val OKHTTP_LOGGING = "4.9.3"

    // Testing dependencies
    const val ANDROIDX_ESPRESSO = "3.4.0"
    const val ANDROIDX_JUNIT = "1.1.3"
    const val JUNIT = "4.13.2"
}

object Libs {
// Android dependencies
    const val androidx_activity_compose = "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}"
    const val androidx_appcompat = "androidx.appcompat:appcompat-resources:${Versions.ANDROIDX_APPCOMPAT}"
    const val androidx_core_ktx = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX}"
    const val androidx_compose = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    const val androidx_compose_material = "androidx.compose.material:material:${Versions.COMPOSE_MATERIAL}"
    const val androidx_compose_navigation = "androidx.navigation:navigation-compose:${Versions.ANDROIDX_NAVIGATION}"
    const val androidx_datastore = "androidx.datastore:datastore-preferences:${Versions.ANDROIDX_DATASTORE}"
    const val androidx_hilt_navigation_compose = "androidx.hilt:hilt-navigation-compose:${Versions.ANDROIDX_HILT}"
    const val androidx_room = "androidx.room:room-ktx:${Versions.ANDROIDX_ROOM}"
    const val androidx_room_compiler = "androidx.room:room-compiler:${Versions.ANDROIDX_ROOM}"
    const val dagger_hilt = "com.google.dagger:hilt-android:${Versions.DAGGER_HILT}"
    const val dagger_hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER_HILT}"
    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}"

    // 3rd party dependencies
    const val coil = "io.coil-kt:coil:${Versions.COIL}"
    const val coil_compose = "io.coil-kt:coil-compose:${Versions.COIL}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_LOGGING}"
    const val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES}"
    const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
    const val kotlinx_serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}"
    const val ktor = "io.ktor:ktor-client-android:${Versions.KTOR}"
    const val ktor_logging = "io.ktor:ktor-client-logging:${Versions.KTOR}"
    const val ktor_negotiation = "io.ktor:ktor-client-content-negotiation:${Versions.KTOR}"
    const val ktor_serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}"

    //Test dependencies
    const val junit = "junit:junit:${Versions.JUNIT}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT}"
    const val androidx_espresso = "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO}"
    const val androidx_compose_ui_test = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
}

object Plugins {
    const val APPLICATION = "com.android.application"
    const val DAGGER_HILT = "dagger.hilt.android.plugin"
    const val DEPENDENCY_UPDATER = "com.github.ben-manes.versions"
    const val KOTLIN_ANDROID = "android"
    const val KOTLIN_KAPT = "kapt"
    const val KOTLIN_SERIALIZATION = "plugin.serialization"
}