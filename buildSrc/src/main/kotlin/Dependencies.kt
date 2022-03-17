object AndroidVersions {
    const val MIN_SDK = 23
    const val TARGET_SDK = 31
    const val COMPILE_SDK = 31
}

object Versions {
    // Android dependencies
    const val ANDROIDX_CORE_KTX = "1.7.0"
    const val ANDROIDX_LIFECYCLE = "2.4.1"
    const val COMPOSE = "1.1.1"
    const val COMPOSE_ACTIVITY = "1.4.0"
    const val PLUGIN_APPLICATION = "7.1.2"

    // 3rd party dependencies
    const val KOTLIN = "1.6.10"
    const val KOTLINX_RETROFIT_CONVERTER = "0.8.0"
    const val KOTLINX_SERIALIZATION = "1.3.2"
    const val OKHTTP_LOGGING = "4.9.2"
    const val RETROFIT = "2.9.0"

    // Testing dependencies
    const val ANDROIDX_ESPRESSO = "3.4.0"
    const val ANDROIDX_JUNIT = "1.1.3"
    const val JUNIT = "4.13.2"
}

object Libs {
// Android dependencies
    const val androidx_core_ktx = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX}"
    const val compose = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val compose_material = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}"
    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}"

    // 3rd party dependencies
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_LOGGING}"
    const val kotlinx_serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}"
    const val kotlinx_serialization_converter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.KOTLINX_RETROFIT_CONVERTER}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"

    //Test dependencies
    const val junit = "junit:junit:${Versions.JUNIT}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT}"
    const val androidx_espresso = "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO}"
    const val androidx_compose_ui_test = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
    const val androidx_compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
}

object Plugins {
    const val APPLICATION = "com.android.application"
    const val KOTLIN_ANDROID = "android"
    const val KOTLIN_KAPT = "kapt"
    const val KOTLIN_SERIALIZATION = "plugin.serialization"
}