package io.github.tscholze.kennzeichner.data

import io.github.tscholze.kennzeichner.utils.makeHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

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

        cachedRegions = client
            .get("https://tscholze.github.io/blog/files/lp-regions-data.json")
            .body()

        return cachedRegions
    }

    /**
     * Gets a region from list of cached regions by id.
     *
     * @param id ID to look up.
     * @return Found region.
     */
    fun regionForId(id: String): Region {
        return cachedRegions.first { it.id == id }
    }
}