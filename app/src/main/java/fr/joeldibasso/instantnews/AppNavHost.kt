package fr.joeldibasso.instantnews

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.composables.LoadingScreen
import fr.joeldibasso.instantnews.ui.composables.NewsAppState
import fr.joeldibasso.instantnews.ui.composables.NewsDetails
import fr.joeldibasso.instantnews.ui.composables.Onboarding
import fr.joeldibasso.instantnews.ui.composables.QrCodeScanScreen
import fr.joeldibasso.instantnews.ui.composables.TopNewsScreen

@Composable
fun AppNavHost(
    startDestination: String,
    navController: NavHostController,
    state: NewsAppState,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        modifier = modifier
    ) {
        navigation("onboarding/welcome", "onboarding") {
            composable(
                "onboarding/welcome",
                enterTransition = {
                    fadeIn(animationSpec = tween(200, easing = EaseOut))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(200, easing = EaseOut))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(200, easing = EaseOut))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(200, easing = EaseOut))
                }
            ) {
                var checkingLoggedIn by remember { mutableStateOf(true) }
                LaunchedEffect(state.isInitialLoading) {
                    if (!state.isInitialLoading && viewModel.uiState.value.isLoggedIn) {
                        navController.navigate("app") {
                            popUpTo("onboarding/welcome") { inclusive = true }
                        }
                    } else {
                        checkingLoggedIn = false
                    }
                }
                AnimatedContent(checkingLoggedIn, label = "", transitionSpec = {
                    fadeIn(animationSpec = tween(200, easing = EaseIn)).togetherWith(
                        fadeOut(animationSpec = tween(200, easing = EaseOut))
                    )
                }) { isChecking ->
                    if (isChecking) {
                        LoadingScreen()
                    } else {
                        Onboarding(
                            viewModel = viewModel,
                            navController = navController,
                        )
                    }
                }
            }
            composable("onboarding/scan_qr",
                enterTransition = {
                    fadeIn(animationSpec = tween(200, easing = EaseOut))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(200, easing = EaseOut))
                }) {
                QrCodeScanScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable("onboarding/type_token",
                enterTransition = {
                    fadeIn(animationSpec = tween(200, easing = EaseOut))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(200, easing = EaseOut))
                }) {
                Onboarding(
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                "onboarding/login_error",
                enterTransition = {
                    fadeIn(animationSpec = tween(200, easing = EaseOut))
                },
                popEnterTransition = {
                    scaleIn(
                        animationSpec = tween(250, easing = EaseOut)
                    ) + fadeIn(animationSpec = tween(200, easing = EaseOut))
                }
            ) {
                Onboarding(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        navigation(
            "app/top_news",
            "app",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
        ) {
            composable(
                "app/top_news",
                enterTransition = {
                    scaleIn(
                        animationSpec = tween(250, easing = EaseOut)
                    ) + fadeIn(animationSpec = tween(200, easing = EaseOut))
                },
                popEnterTransition = {
                    scaleIn(
                        animationSpec = tween(250, easing = EaseOut)
                    ) + fadeIn(animationSpec = tween(200, easing = EaseOut))
                },
                exitTransition = {
                    slideOutOfContainer(
                        animationSpec = tween(250, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                }
            ) {
                TopNewsScreen(
                    viewModel = viewModel,
                    navController = navController,
                )
            }

            composable(
                "app/details/{index}",
                arguments = listOf(navArgument("index") {
                    type = NavType.IntType
                }),
                enterTransition = {
                    slideIntoContainer(
                        animationSpec = tween(250, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        animationSpec = tween(250, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    ) + fadeOut(animationSpec = tween(200, easing = EaseIn))
                }
            ) {
                val index = it.arguments?.getInt("index")
                state.topNews.getOrNull(index ?: 0)?.let { news ->
                    NewsDetails(news = news)
                } ?: run {
                    LoadingScreen()
                }
            }
        }
    }
}