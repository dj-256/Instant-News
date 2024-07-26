package fr.joeldibasso.instantnews.ui.composables

import fr.joeldibasso.instantnews.model.News

/**
 * Immutable data class that holds the state of the News screen.
 */
data class NewsAppState(
    val isLoading: Boolean = false,
    // Initial loading state is used to show a loading spinner when the app is first launched
    val isInitialLoading: Boolean = true,
    val topNews: List<News> = emptyList(),
    val darkMode: Boolean = false,
    val isLoggedIn: Boolean = false,
    val token: String? = null,
    // Is set to true when the user tries to log in with invalid credentials
    val isLoginError: Boolean = false,
    // Used to remember the login method the user selected while in the onboarding screens
    val loginMethod: LoginMethod = LoginMethod.QR_CODE
)

/**
 * Enum class that represents the possible login methods in the app.
 */
enum class LoginMethod {
    QR_CODE,
    TOKEN
}