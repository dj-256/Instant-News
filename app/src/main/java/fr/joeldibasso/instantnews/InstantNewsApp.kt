package fr.joeldibasso.instantnews

import android.app.Application
import fr.joeldibasso.instantnews.api.NewsClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InstantNewsApp : Application() {
    companion object {
        val retrofitInstance: NewsClient by lazy {
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val newRequest: Request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer <token>")
                        .build()
                    return chain.proceed(newRequest)
                }
            }).build()
            Retrofit.Builder()
                .client(client)
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsClient::class.java)
        }
    }
}