package io.github.tscholze.kennzeichner.utils

import io.ktor.client.HttpClient

/**
 * Multi platform factory for providing a http client with
 * a platform specific engine to work with.
 *
 * Caution:
 *  The engine `CIO` would work in common but does not support
 *  TLS (https) on native targets.
 *
 * iOS: iOSMain/utils/
 * Android: androidMain/utils/
 */
expect fun makeHttpClient(): HttpClient