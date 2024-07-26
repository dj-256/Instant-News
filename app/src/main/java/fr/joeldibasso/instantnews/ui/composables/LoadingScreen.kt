package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

/**
 * Simple loading screen with a circular progress indicator.
 * @param modifier The modifier for the loading screen.
 */
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
fun LoadingScreenPreview() {
    InstantNewsTheme {
        Surface {
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        Surface {
            LoadingScreen()
        }
    }
}