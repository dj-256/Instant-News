package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.joeldibasso.instantnews.ui.NewsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@Composable
fun TopNewsScreen(modifier: Modifier = Modifier, viewModel: NewsViewModel = viewModel()) {

    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.getTopNews()
    }
    Surface(
        modifier = modifier
            .padding(16.dp)
    ) {
        AnimatedContent(targetState = state.isLoading, label = "loading") { isLoading ->
            if (isLoading) {
                LoadingScreen()
            } else {
                Column {
                    Text(text = "Top News", style = MaterialTheme.typography.headlineLarge, color = Color(0xFFE6E6EF))

                    LazyColumn(modifier = modifier) {
                        items(state.topNews) { news ->
                            NewsCard(
                                title = news.title,
                                description = news.description,
                                imageUrl = news.imageUrl
                            )
                        }
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