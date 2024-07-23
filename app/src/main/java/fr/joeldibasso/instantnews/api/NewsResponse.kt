package fr.joeldibasso.instantnews.api

data class SourceField(
    val id: String,
    val name: String
)

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

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
    val source: Article
)