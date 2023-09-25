@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("android")
}

android {
    namespace = "io.github.tscholze.kennzeichner.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "io.github.tscholze.kennzeichner.android"
        minSdk = 31
        targetSdk = 33
        versionCode = 1
        versionName = "0.3.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))

    with(Dependencies.KotlinX) {
        implementation(coroutines)
    }

    with(Dependencies.Maps) {
        implementation(compose)
        implementation(utils)
        implementation(playServices)
    }

    with(Dependencies.AndroidX) {
        implementation(ui)
        implementation(tooling)
        implementation(toolingPreview)
        implementation(foundation)
        implementation(material)
        implementation(navigation)
        implementation(activity)
    }

    with(Dependencies.Koin) {
        implementation(compose)
    }
}

object Dependencies {
    object KotlinX {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlinx.version}"
    }

    object Maps {
        const val compose = "com.google.maps.android:maps-compose:${Versions.Maps.version}"
        const val utils = "com.google.maps.android:maps-compose-utils:${Versions.Maps.version}"
        const val playServices = "com.google.android.gms:play-services-maps:${Versions.Maps.playServices}"
    }

    object AndroidX {
        const val ui = "androidx.compose.ui:ui:${Versions.Androidx.version}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.Androidx.version}"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.Androidx.version}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.Androidx.version}"
        const val material = "androidx.compose.material:material:${Versions.Androidx.version}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.Androidx.navigation}"
        const val activity = "androidx.activity:activity-compose:${Versions.Androidx.activity}"
    }

    object Koin {
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.Koin.version}"
    }

    private object Versions {
        object Androidx {
            const val version = "1.4.0"
            const val navigation = "2.5.3"
            const val activity = "1.7.0"
        }
        object Kotlinx {
            const val version = "1.6.4"
        }

        object Maps {
            const val version = "2.11.2"
            const val playServices = "18.1.0"
        }

        object Koin {
            const val version = "3.4.3"
        }
    }
}