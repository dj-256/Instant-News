package fr.joeldibasso.instantnews.api

import fr.joeldibasso.instantnews.model.News
import io.ktor.client.HttpClient

class NewsClientImpl(
    private val client: HttpClient
): NewsClient {
    override suspend fun getTopNews(): List<News> {
        return listOf(
            News(
                title = "Craig Wright Faces Perjury Investigation Over Claims He Created Bitcoin",
                description = "By order of a UK judge, Craig Wright can no longer claim he is the creator of bitcoin and now faces the prospect of criminal charges.",
                imageUrl = "https://media.wired.com/photos/6696630a5d2d61e4805c3175/191:100/w_1280,c_limit/GettyImages-1979197796.jpg"
            ),
        )
    }
}