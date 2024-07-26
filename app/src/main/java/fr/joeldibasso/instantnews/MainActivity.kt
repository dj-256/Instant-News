package fr.joeldibasso.instantnews

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.composables.DarkModeToggle
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        val viewModel: NewsViewModel by viewModels()
        val prefs = this.getSharedPreferences("instant_news", 0)
        val token = prefs.getString("token", null)
        if (token != null) {
            viewModel.getTopNews(token)
        } else {
            viewModel.finishInitialLoad()
        }

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val state by viewModel.uiState.collectAsState()
            val darkMode = state.darkMode
            InstantNewsTheme(darkTheme = darkMode) {
                Scaffold(
                    topBar = {
                        AnimatedVisibility(currentRoute?.startsWith("app") == true) {
                            TopAppBar(
                                title = {
                                    AnimatedVisibility(
                                        currentRoute == "app/top_news",
                                        enter = scaleIn(
                                            tween(
                                                durationMillis = 200,
                                                easing = EaseOut
                                            )
                                        ) + fadeIn(
                                            tween(
                                                durationMillis = 150,
                                                easing = EaseIn
                                            )
                                        ),
                                        exit = ExitTransition.None
                                    ) {
                                        Text(
                                            text = "Top News",
                                            style = MaterialTheme.typography.headlineLarge,
                                        )
                                    }
                                },
                                navigationIcon = {
                                    if (currentRoute?.startsWith("app/details") == true) {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                                contentDescription = "Back"
                                            )
                                        }
                                    }
                                },
                                actions = {
                                    DarkModeToggle(isDarkMode = state.darkMode) {
                                        viewModel.toggleDarkMode()
                                    }
                                },
                                colors = TopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                                )
                            )
                        }
                    },
                ) { innerPadding ->
                    AppNavHost(
                        startDestination = "onboarding",
                        navController = navController,
                        state = state,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}