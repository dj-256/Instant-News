package fr.joeldibasso.instantnews

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.composables.TopNewsScreen
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NewsViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            val state by viewModel.uiState.collectAsState()
            val darkMode = state.darkMode
            val view = LocalView.current
            if (!view.isInEditMode) {
                SideEffect {
                    val window = (view.context as Activity).window
                    WindowCompat.getInsetsController(window, view).apply {
                        isAppearanceLightStatusBars = !darkMode
                        isAppearanceLightNavigationBars = !darkMode
                    }
                }
            }
            InstantNewsTheme(darkTheme = darkMode) {
                    TopNewsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
        }
    }
}