package fr.joeldibasso.instantnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.joeldibasso.instantnews.InstantNewsApp
import fr.joeldibasso.instantnews.ui.composables.TopNewsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TopNewsScreenState())
    val uiState: StateFlow<TopNewsScreenState> = _uiState.asStateFlow()

    fun getTopNews() {
        viewModelScope.launch {
            _uiState.value = TopNewsScreenState(isLoading = true)
            val news = InstantNewsApp.newsClient.getTopNews()
            _uiState.value = TopNewsScreenState(topNews = news, isLoading = false)
        }
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(darkMode = !_uiState.value.darkMode)
    }
}