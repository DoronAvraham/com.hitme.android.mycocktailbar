object AndroidVersions {
    const val MIN_SDK = 23
    const val TARGET_SDK = 31
    const val COMPILE_SDK = 31
}

object Versions {
    // Android dependencies
    const val AGP = "7.2.0"
    const val ANDROIDX_APPCOMPAT = "1.4.1"
    const val ANDROIDX_CORE_KTX = "1.7.0"
    const val ANDROIDX_HILT = "1.0.0"
    const val ANDROIDX_NAVIGATION = "2.4.1"
    const val ANDROIDX_LIFECYCLE = "2.4.1"
    const val ANDROIDX_ROOM = "2.4.2"
    const val COMPOSE = "1.2.0-alpha07"
    const val COMPOSE_ACTIVITY = "1.4.0"
    const val DAGGER_HILT = "2.40"

    // 3rd party dependencies
    const val GLIDE = "4.13.1"
    const val KOTLIN = "1.6.10"
    const val KOTLINX_COROUTINES = "1.6.0"
    const val KOTLINX_RETROFIT_CONVERTER = "0.8.0"
    const val KOTLINX_SERIALIZATION = "1.3.2"
    const val KTLINT = "10.2.1"
    const val OKHTTP_LOGGING = "4.9.2"
    const val RETROFIT = "2.9.0"

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
    const val androidx_compose_material = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val androidx_compose_navigation = "androidx.navigation:navigation-compose:${Versions.ANDROIDX_NAVIGATION}"
    const val androidx_hilt_navigation_compose = "androidx.hilt:hilt-navigation-compose:${Versions.ANDROIDX_HILT}"
    const val androidx_room = "androidx.room:room-ktx:${Versions.ANDROIDX_ROOM}"
    const val androidx_room_compiler = "androidx.room:room-compiler:${Versions.ANDROIDX_ROOM}"
    const val dagger_hilt = "com.google.dagger:hilt-android:${Versions.DAGGER_HILT}"
    const val dagger_hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER_HILT}"
    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}"

    // 3rd party dependencies
    const val glide = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_LOGGING}"
    const val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES}"
    const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
    const val kotlinx_serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}"
    const val kotlinx_serialization_converter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.KOTLINX_RETROFIT_CONVERTER}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"

    //Test dependencies
    const val junit = "junit:junit:${Versions.JUNIT}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT}"
    const val androidx_espresso = "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO}"
    const val androidx_compose_ui_test = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
}

object Plugins {
    const val APPLICATION = "com.android.application"
    const val DAGGER_HILT = "dagger.hilt.android.plugin"
    const val KOTLIN_ANDROID = "android"
    const val KOTLIN_KAPT = "kapt"
    const val KOTLIN_SERIALIZATION = "plugin.serialization"
}