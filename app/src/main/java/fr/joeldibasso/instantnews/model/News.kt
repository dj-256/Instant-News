package fr.joeldibasso.instantnews.model

/**
 * Data class representing a news article.
 */
data class News(
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val source: String,
    val content: String? = null
)