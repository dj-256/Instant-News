package fr.joeldibasso.instantnews.model

data class News(
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val source: String,
    val content: String? = null
)