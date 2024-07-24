package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.joeldibasso.instantnews.R
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {

    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.getTopNews()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Top News",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
                actions = {
                    AnimatedContent(
                        targetState = state.darkMode,
                        label = "dark mode icon",
                        transitionSpec = {
                            (fadeIn(animationSpec = tween(300)) + scaleIn()).togetherWith(
                                fadeOut(animationSpec = tween(300))
                            )
                        }
                    ) { darkMode ->
                        Icon(
                            painter = painterResource(id = if (darkMode) R.drawable.light_mode else R.drawable.dark_mode),
                            contentDescription = "Toggle dark mode icon",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Switch(
                        checked = state.darkMode,
                        onCheckedChange = { viewModel.toggleDarkMode() })
                    Spacer(modifier = Modifier.width(4.dp))
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        AnimatedContent(targetState = state.isLoading, label = "loading") { isLoading ->
            if (isLoading) {
                LoadingScreen()
            } else {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        item { Spacer(modifier = Modifier.height(4.dp)) }
                        itemsIndexed(state.topNews) { index, news ->
                            NewsCard(news = news) {
                                navController.navigate("details/$index")
                            }
                        }
                        item { Spacer(modifier = Modifier.height(32.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(text = "Loading...", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun TopNewsScreenPreview() {
    InstantNewsTheme {
        TopNewsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsScreenPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        TopNewsScreen()
    }
}