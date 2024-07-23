package fr.joeldibasso.instantnews.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface NewsClient {
    @GET("/v2/top-headlines")
    suspend fun getTopNews(@Query("language") language: String = Locale.getDefault().language): Response<NewsResponse>
}