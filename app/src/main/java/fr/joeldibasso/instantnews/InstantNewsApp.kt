package fr.joeldibasso.instantnews

import android.app.Application
import fr.joeldibasso.instantnews.api.NewsClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InstantNewsApp : Application() {
    companion object {
        // Singleton instance of the NewsClient
        val retrofitInstance: NewsClient by lazy {
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsClient::class.java)
        }
    }
}