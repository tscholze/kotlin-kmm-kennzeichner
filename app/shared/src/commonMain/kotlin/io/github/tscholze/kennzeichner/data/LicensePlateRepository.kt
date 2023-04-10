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
     * filters region by given search query.
     * If no regions are fetched, the data source will be updated first.
     *
     * @param searchQuery User's search query
     * @return List of filtered regions.
     */
    suspend fun regionsForSearchQuery(searchQuery: String): List<Region> {
        if (cachedRegions.isEmpty()) {
            fetchRegions()
        }

        val sanitizedQuery = searchQuery.trim()

        if(sanitizedQuery.isEmpty()) {
            return cachedRegions
        }

        return cachedRegions.filter { region ->
            region.id.contains(sanitizedQuery, ignoreCase = true)
                    || region.name.contains(sanitizedQuery, ignoreCase = true)
        }
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