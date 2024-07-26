package fr.joeldibasso.instantnews.ui.composables

import fr.joeldibasso.instantnews.model.News

data class NewsAppState(
    val isLoading: Boolean = false,
    val isInitialLoading: Boolean = true,
    val topNews: List<News> = emptyList(),
    val error: String = "",
    val darkMode: Boolean = false,
    val isLoggedIn: Boolean = false,
    val token: String? = null,
    val isLoginError: Boolean = false,
    val loginMethod: LoginMethod = LoginMethod.QR_CODE
)


enum class LoginMethod {
    QR_CODE,
    TOKEN
}