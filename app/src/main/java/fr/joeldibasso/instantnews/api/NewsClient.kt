package fr.joeldibasso.instantnews.api

import retrofit2.Response
import retrofit2.http.GET

interface NewsClient {
    @GET("/v2/top-headlines?country=us")
    suspend fun getTopNews(): Response<NewsResponse>
}