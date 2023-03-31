package io.github.tscholze.kennzeichner.data

import kotlinx.serialization.Serializable

@Serializable
data class Region (
    val id: String,
    val name: String,
    val lat: String,
    val long: String,
    val inhabitants: String,
    val leader: String,
    val area: String
)