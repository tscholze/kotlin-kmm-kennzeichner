package io.github.tscholze.kennzeichner.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Android implementation a http client with
 * a platform specific engine to work with.
 *
 * Caution:
 * The engine `CIO` would work in common but does not support
 * TLS (https) on native targets.
 *
 * Common: commonMain/utils/
 * iOS: iOSMain/utils/
 */
actual fun makeHttpClient(): HttpClient {
    return HttpClient(CIO) {
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