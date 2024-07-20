package fr.joeldibasso.instantnews.api

import fr.joeldibasso.instantnews.model.News
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

interface NewsClient {
    suspend fun getTopNews(): List<News>

    companion object {
        fun create(): NewsClient {
            return NewsClientImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }

                    install(ContentNegotiation) {
                        json()
                    }
                }
            )
        }
    }
}