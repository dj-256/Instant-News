package fr.joeldibasso.instantnews

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.composables.Onboarding
import fr.joeldibasso.instantnews.ui.composables.QrCodeScanScreen
import fr.joeldibasso.instantnews.ui.composables.TopNewsScreen
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NewsViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            val context = LocalView.current.context
            val navController = rememberNavController()
            val state by viewModel.uiState.collectAsState()

            LaunchedEffect(key1 = true) {
                val prefs = context.getSharedPreferences("instant_news", 0)
                val token = prefs.getString("token", null)
                if (token != null) {
                    viewModel.checkToken(token)
                }
            }
            LaunchedEffect(key1 = state.isLoggedIn) {
                if (state.isLoggedIn) {
                    navController.navigate("top_news")
                }
            }
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
                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") {
                        Onboarding(navController = navController)
                    }
                    composable("scan_qr") {
                        QrCodeScanScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("top_news") {
                        TopNewsScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}