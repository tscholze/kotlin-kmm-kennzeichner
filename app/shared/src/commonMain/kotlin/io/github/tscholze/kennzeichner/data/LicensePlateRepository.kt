package io.github.tscholze.kennzeichner.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class LicensePlateRepository {
    private val client = HttpClient()

    suspend fun demo(): String {
        val response = client.get("https://raw.githubusercontent.com/tscholze/kotlin-kmm-kennzeichner/main/data/data.json")
        return response.bodyAsText()
    }
}