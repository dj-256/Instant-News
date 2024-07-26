package fr.joeldibasso.instantnews.api

/**
 * Helper data class to represent the "source" field in the News API response
 */
data class SourceField(
    val id: String,
    val name: String
)

/**
 * Helper data class to represent the "article" field in the News API response
 */
data class Article(
    val source: SourceField,
    val author: String,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

/**
 * Data class to represent the response from the News API
 */
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
    val source: Article
)