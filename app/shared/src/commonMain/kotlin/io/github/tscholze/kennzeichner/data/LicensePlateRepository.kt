package io.github.tscholze.kennzeichner.data

import io.github.tscholze.kennzeichner.utils.makeHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class LicensePlateRepository {
    private val client = makeHttpClient()

    /**
     * Fetches async region from remote
     */
    suspend fun fetchRegions(): List<Region> {
        return client
            .get("https://tscholze.github.io/blog/files/lp-regions-data.json")
            .body()
    }
}