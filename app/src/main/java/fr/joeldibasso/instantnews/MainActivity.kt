package fr.joeldibasso.instantnews

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.composables.LoadingScreen
import fr.joeldibasso.instantnews.ui.composables.NewsDetails
import fr.joeldibasso.instantnews.ui.composables.Onboarding
import fr.joeldibasso.instantnews.ui.composables.QrCodeScanScreen
import fr.joeldibasso.instantnews.ui.composables.TopNewsScreen
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NewsViewModel by viewModels()
        val prefs = this.getSharedPreferences("instant_news", 0)
        val token = prefs.getString("token", null)
        if (token != null) {
            viewModel.checkToken(token)
        }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
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
                NavHost(
                    navController = navController,
                    startDestination = if (state.isLoggedIn) "top_news" else "onboarding"
                ) {
                    composable("onboarding") {
                        AnimatedVisibility(visible = !state.isLoading) {
                            Onboarding(navController = navController)
                        }
                    }
                    composable("scan_qr") {
                        QrCodeScanScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("top_news") {
                        TopNewsScreen(viewModel = viewModel, navController = navController)
                    }

                    composable(
                        "details/{index}",
                        arguments = listOf(navArgument("index") { type = NavType.IntType })
                    ) {
                        val index = it.arguments?.getInt("index")
                        state.topNews.getOrNull(index ?: 0)?.let { news ->
                            NewsDetails(news = news, navController = navController)
                        } ?: run {
                            LoadingScreen()
                        }
                    }
                }
            }
        }
    }
}