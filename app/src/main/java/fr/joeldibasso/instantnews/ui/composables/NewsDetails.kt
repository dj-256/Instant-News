package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.joeldibasso.instantnews.model.News
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@Composable
fun NewsDetails(
    news: News,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(text = news.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.source,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            news.description?.let {
                Text(
                    text = news.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            news.urlToImage?.let {
                AsyncImage(
                    model = it, contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            news.content?.let { Text(text = news.content) }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(bottom = 40.dp)
        ) {
            Button(onClick = {
                uriHandler.openUri(news.url)
            }) {
                Text(
                    text = "Read more",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
            }
            Text(
                text = "Open in browser",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewsDetailsPreview() {
    InstantNewsTheme {
        Surface {
            NewsDetails(
                news = News(
                    title = "Title",
                    description = "Description",
                    url = "https://www.example.com",
                    source = "Example"
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewsDetailsPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        Surface {
            NewsDetails(
                news = News(
                    title = "Title",
                    description = "Description",
                    url = "https://www.example.com",
                    urlToImage = "https://www.example.com/image.jpg",
                    source = "Example"
                )
            )
        }
    }
}