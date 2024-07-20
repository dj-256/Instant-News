package fr.joeldibasso.instantnews.model

import kotlinx.serialization.Serializable

@Serializable
data class News(val title: String, val description: String, val imageUrl: String)