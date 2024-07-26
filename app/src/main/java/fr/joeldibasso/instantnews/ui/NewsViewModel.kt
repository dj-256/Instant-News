package fr.joeldibasso.instantnews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.joeldibasso.instantnews.InstantNewsApp
import fr.joeldibasso.instantnews.model.News
import fr.joeldibasso.instantnews.ui.composables.LoginMethod
import fr.joeldibasso.instantnews.ui.composables.NewsAppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NewsAppState())
    val uiState: StateFlow<NewsAppState> = _uiState.asStateFlow()

    fun getTopNews(token: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = InstantNewsApp.retrofitInstance.getTopNews("Bearer $token")
            if (!response.isSuccessful) {
                Log.e(
                    "NewsViewModel",
                    ("Error fetching news" + response.errorBody()?.string())
                )
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isInitialLoading = false,
                    isLoginError = true
                )
                return@launch
            }
            val news = response.body()?.articles?.map {
                News(
                    title = it.title.substringBeforeLast("-"),
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    source = it.source.name,
                    content = it.content?.substringBeforeLast("[")
                )
            } ?: emptyList()
            _uiState.value =
                _uiState.value.copy(
                    topNews = news,
                    isLoading = false,
                    isLoggedIn = true,
                    isInitialLoading = false,
                    token = token
                )
        }
    }

    fun updateToken(token: String?) {
        _uiState.value = _uiState.value.copy(token = token)
    }

    fun finishInitialLoad() {
        _uiState.value = _uiState.value.copy(isLoading = false, isInitialLoading = false)
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(darkMode = !_uiState.value.darkMode)
    }

    fun setLoginMethod(loginMethod: LoginMethod) {
        _uiState.value = _uiState.value.copy(loginMethod = loginMethod)
    }

    fun clearLogginError() {
        _uiState.value = _uiState.value.copy(isLoginError = false)
    }
}