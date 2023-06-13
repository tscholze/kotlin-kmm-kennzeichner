package io.github.tscholze.kennzeichner.data.dto

import kotlinx.serialization.Serializable

/**
 * Data transfer model of a remote
 * region.
 */
@Serializable
data class RegionDTO (
    val id: String,
    val name: String,
    val lat: Double,
    val long: Double,
    val inhabitants: Int,
    val leader: String? = null,
    val area: Int? = null
)