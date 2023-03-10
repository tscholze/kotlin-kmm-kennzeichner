package io.github.tscholze.kennzeichner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform