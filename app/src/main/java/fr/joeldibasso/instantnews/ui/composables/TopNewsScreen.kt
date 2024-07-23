package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.joeldibasso.instantnews.R
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@Composable
fun TopNewsScreen(modifier: Modifier = Modifier, viewModel: NewsViewModel = viewModel()) {

    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.getTopNews()
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 4.dp)
            ) {
                Text(
                    text = "Top News",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
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
                    Switch(
                        checked = state.darkMode,
                        onCheckedChange = { viewModel.toggleDarkMode() })
                }
            }
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
                        items(state.topNews) { news ->
                            NewsCard(news = news)
                        }
                        item { Spacer(modifier = Modifier.height(32.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Text(text = "Loading...")
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