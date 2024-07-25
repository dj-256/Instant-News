package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@Composable
fun TopNewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {

    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    LaunchedEffect(null) {
        viewModel.getTopNews()
    }
    AnimatedContent(state.isLoading, label = "", transitionSpec = {
        (scaleIn(
            animationSpec = tween(250, easing = EaseOut)
        ) + fadeIn(animationSpec = tween(200, easing = EaseIn))).togetherWith(
            fadeOut(animationSpec = tween(250, easing = EaseOut))
        )
    }) { isLoading ->
        if (isLoading) {
            LoadingScreen(modifier = modifier)
        } else {
            Column(
                modifier = modifier
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = scrollState,
                ) {
                    item { Spacer(modifier = Modifier.height(4.dp)) }
                    itemsIndexed(state.topNews) { index, news ->
                        NewsCard(news = news) {
                            navController.navigate("app/details/$index")
                        }
                    }
                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
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