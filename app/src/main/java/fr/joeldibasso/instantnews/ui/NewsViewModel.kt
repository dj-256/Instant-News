package fr.joeldibasso.instantnews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import fr.joeldibasso.instantnews.InstantNewsApp
import fr.joeldibasso.instantnews.model.News
import fr.joeldibasso.instantnews.ui.composables.TopNewsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class NewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TopNewsScreenState())
    val uiState: StateFlow<TopNewsScreenState> = _uiState.asStateFlow()

    fun getTopNews() {
        viewModelScope.launch {
            _uiState.value = TopNewsScreenState(isLoading = true)
            val response = try {
                InstantNewsApp.retrofitInstance.getTopNews()
            } catch (e: IOException) {
                Log.e("NewsViewModel", "Error fetching news", e)
                return@launch
            } catch (e: HttpException) {
                Log.e("NewsViewModel", "Error fetching news", e)
                return@launch
            }
            val news = response.body()?.articles?.map {
                News(
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    source = it.source.name
                )
            } ?: emptyList()
            _uiState.value = TopNewsScreenState(topNews = news, isLoading = false)
        }
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(darkMode = !_uiState.value.darkMode)
    }
}