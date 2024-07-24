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

    fun checkToken(token: String) {
        _uiState.value = _uiState.value.copy(isInitialLoading = true)
        viewModelScope.launch {
            try {
                InstantNewsApp.retrofitInstance.getTopNews("Bearer $token")
                _uiState.value = _uiState.value.copy(isLoggedIn = true, token = token)
                Log.d("NewsViewModel", "_uiState.value.token1: ${_uiState.value.token}")
            } catch (e: IOException) {
                Log.e("NewsViewModel", "Error fetching news", e)
                return@launch
            } catch (e: HttpException) {
                Log.e("NewsViewModel", "Error fetching news", e)
                return@launch
            } finally {
                _uiState.value = _uiState.value.copy(isInitialLoading = false)
            }
        }
    }

    fun getTopNews() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = try {
                InstantNewsApp.retrofitInstance.getTopNews("Bearer ${_uiState.value.token}")
            } catch (e: IOException) {
                Log.e("NewsViewModel", "Error fetching news", e)
                return@launch
            } catch (e: HttpException) {
                Log.e("NewsViewModel", "Error fetching news", e)
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
            _uiState.value = _uiState.value.copy(topNews = news, isLoading = false)
        }
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(darkMode = !_uiState.value.darkMode)
    }
}