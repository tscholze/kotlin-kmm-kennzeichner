package io.github.tscholze.kennzeichner.data

import io.github.tscholze.kennzeichner.data.dto.RegionDTO
import io.github.tscholze.kennzeichner.utils.makeHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * This repository contains all properties and features
 * to work with remotely fetched license plate regions.
 */
class LicensePlateRepository {

    // MARK: - Private properties -

    private val client = makeHttpClient()
    private var cachedRegions = emptyList<Region>()

    // MARK: - Access methods -

    /**
     * Fetches async region from remote
     */
    suspend fun fetchRegions(): List<Region> {

        if(cachedRegions.isNotEmpty()) {
            return cachedRegions
        }

        cachedRegions  = client
            .get("https://tscholze.github.io/blog/files/lp-regions-data.json")
            .body<List<RegionDTO>>()
            .mapNotNull {  Region.fromDto(it) }

        return cachedRegions
    }

    /**
     * Gets a region from list of cached regions by id.
     *
     * @param id ID to look up.
     * @return Found region.
     */
    suspend fun regionForId(id: String): Region {
        val region = cachedRegions.find { it.id == id }

        return if (region == null) {
            fetchRegions()
            regionForId(id)
        } else {
            region
        }
    }
}