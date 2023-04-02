plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.7.10"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val ktorVersion = "2.2.4"

        val commonMain by getting {
            dependencies {
                // Kotlinx
                with(Dependencies.Kotlinx) {
                    implementation(coroutines)
                }

                // Ktor
                with(Dependencies.Ktor) {
                    implementation(core)
                    implementation(contentNegotiation)
                    implementation(json)
                }
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                // Ktor
                with(Dependencies.Ktor) {
                    implementation(cio)
                }
            }

        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                // Ktor
                with(Dependencies.Ktor) {
                    implementation(darwin)
                }
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "io.github.tscholze.kennzeichner"
    compileSdk = 33
    defaultConfig {
        minSdk = 31
        targetSdk = 33
    }
}

// MARK: - Dependencies -

/**
 * Contains all gradle dependencies.
 *
 * Usage:
 * ```
 *     with(Dependencies.Kotlinx) {
 *         implement(coroutines)
 *     }
 * ```
 */
object Dependencies {
    object Kotlinx {
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlinx.version}"
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Versions.Ktor.version}"
        const val cio = "io.ktor:ktor-client-cio:${Versions.Ktor.version}"
        const val contentNegotiation =
            "io.ktor:ktor-client-content-negotiation:${Versions.Ktor.version}"
        const val json = "io.ktor:ktor-serialization-kotlinx-json:${Versions.Ktor.version}"
        const val darwin = "io.ktor:ktor-client-darwin:${Versions.Ktor.version}"
    }

    // MARK: - Versions -
    private object Versions {
        object Ktor {
            const val version = "2.1.3"
        }

        object Kotlinx {
            const val version = "1.6.4"
        }
    }
}

