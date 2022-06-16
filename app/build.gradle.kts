import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id(Plugins.APPLICATION)
    id(Plugins.DAGGER_HILT)
    id(Plugins.DEPENDENCY_UPDATER)
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

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-rules-kotlinx.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        resources.excludes.add("META-INF/atomicfu.kotlin_module")
    }
}

dependencies {

    // Android dependencies
    implementation(Libs.androidx_activity_compose)
    implementation(Libs.androidx_appcompat)
    implementation(Libs.androidx_compose)
    implementation(Libs.androidx_compose_material)
    implementation(Libs.androidx_compose_navigation)
    implementation(Libs.androidx_core_ktx)
    implementation(Libs.androidx_datastore)
    implementation(Libs.androidx_hilt_navigation_compose)
    implementation(Libs.androidx_room)
    kapt(Libs.androidx_room_compiler)
    implementation(Libs.dagger_hilt)
    kapt(Libs.dagger_hilt_compiler)
    implementation(Libs.lifecycle_runtime_ktx)

    // 3rd party dependencies
    implementation(Libs.glide)
    kapt(Libs.glide_compiler)
    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_serialization)
    implementation(Libs.kotlinx_serialization_converter)
    implementation(Libs.okhttp_logging)
    implementation(Libs.retrofit)

    // Test dependencies
    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidx_junit)
    androidTestImplementation(Libs.androidx_espresso)
    androidTestImplementation(Libs.androidx_compose_ui_test)
}

// running "./gradlew app:dependencyUpdates" will produce a list of all updatable dependencies.
// https://github.com/ben-manes/gradle-versions-plugin
// We filter out non wanted versions.
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {

    val nonStableTags = listOf("alpha", "beta", "rc")

    fun isNonStable(version: String) = nonStableTags.any { tag -> version.contains(tag, ignoreCase = true) }

    // Prevents from updating from stable to non-stable version.
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
