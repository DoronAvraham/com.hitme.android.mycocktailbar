plugins {
    id(Plugins.APPLICATION)
    id(Plugins.DAGGER_HILT)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    kotlin(Plugins.KOTLIN_SERIALIZATION)
}

android {
    compileSdk = AndroidVersions.COMPILE_SDK

    defaultConfig {
        applicationId = "com.hitme.android.mycocktailbar"
        minSdk = AndroidVersions.MIN_SDK
        targetSdk = AndroidVersions.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro",
                "proguard-rules-kotlinx.pro")
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {

    // Android dependencies
    implementation(Libs.androidx_core_ktx)
    implementation(Libs.compose)
    implementation(Libs.compose_activity)
    implementation(Libs.compose_material)
    implementation(Libs.compose_ui_tooling_preview)
    implementation(Libs.dagger_hilt)
    kapt(Libs.dagger_hilt_compiler)
    implementation(Libs.lifecycle_runtime_ktx)
    implementation(Libs.lifecycle_livedata_ktx)

    // 3rd party dependencies
    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_serialization)
    implementation(Libs.kotlinx_serialization_converter)
    implementation(Libs.okhttp_logging)
    implementation(Libs.retrofit)

    //Test dependencies
    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidx_junit)
    androidTestImplementation(Libs.androidx_espresso)
    androidTestImplementation(Libs.androidx_compose_ui_test)
    debugImplementation(Libs.androidx_compose_ui_tooling)
}