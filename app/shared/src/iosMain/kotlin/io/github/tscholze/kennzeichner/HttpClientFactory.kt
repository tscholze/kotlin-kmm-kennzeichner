package io.github.tscholze.kennzeichner.utils

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * iOS implementation a http client with
 * a platform specific engine to work with.
 *
 * Caution:
 * The engine `CIO` would work in common but does not support
 * TLS (https) on native targets.
 *
 * Common: commonMain/utils/
 * Android: androidMain/utils/
 */
actual fun makeHttpClient(): HttpClient {
    return HttpClient(Darwin) {
        // Configure http client.
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}