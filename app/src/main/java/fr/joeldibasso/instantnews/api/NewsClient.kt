package fr.joeldibasso.instantnews.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Locale

/**
 * Interface for the Retrofit client
 */
interface NewsClient {
    @GET("/v2/top-headlines")
    suspend fun getTopNews(
        // The token is passed as a dynamic header
        @Header("Authorization") tokenField: String,
        // Get language from the device
        @Query("language") language: String = Locale.getDefault().language
    ): Response<NewsResponse>

}