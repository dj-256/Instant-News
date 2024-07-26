package fr.joeldibasso.instantnews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.joeldibasso.instantnews.InstantNewsApp
import fr.joeldibasso.instantnews.model.News
import fr.joeldibasso.instantnews.ui.composables.TopNewsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class NewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TopNewsScreenState())
    val uiState: StateFlow<TopNewsScreenState> = _uiState.asStateFlow()

    fun getTopNews(token: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val response = InstantNewsApp.retrofitInstance.getTopNews("Bearer $token")
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
            } catch (e: IOException) {
                Log.e("NewsViewModel", "Error fetching news", e)
                _uiState.value = _uiState.value.copy(isLoading = false, isInitialLoading = false)
                return@launch
            } catch (e: HttpException) {
                Log.e("NewsViewModel", "Error fetching news", e)
                _uiState.value = _uiState.value.copy(isLoading = false, isInitialLoading = false)
                return@launch
            }

        }
    }

    fun finishInitialLoad() {
        _uiState.value = _uiState.value.copy(isLoading = false, isInitialLoading = false)
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(darkMode = !_uiState.value.darkMode)
    }
}