package fr.joeldibasso.instantnews.ui.composables

import fr.joeldibasso.instantnews.model.News

data class TopNewsScreenState(
    val isLoading: Boolean = false,
    val topNews: List<News> = emptyList(),
    val error: String = "",
    val darkMode: Boolean = false,
    val isLoggedIn: Boolean = false,
    val token: String? = null
)
