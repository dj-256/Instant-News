package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.joeldibasso.instantnews.model.News
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

/**
 * NewsCard is the composable that displays news information in the TopNewsScreen list.
 * It navigates to the NewsDetails screen when clicked.
 * @param news The news to display.
 * @param modifier The modifier for the NewsCard.
 * @param onClick The action to perform when the card is clicked.
 */
@Composable
fun NewsCard(news: News, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(12.dp)
        ) {
            news.urlToImage?.let {
                AsyncImage(
                    model = news.urlToImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = news.title,
                    // Limit the number of lines to 3 if there is no image. This ensures
                    // that the card height is consistent across all image-less cards.
                    minLines = news.urlToImage?.let { 1 } ?: 3,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.testTag("news_title")
                )
                Text(
                    text = news.source,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.testTag("news_source")
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    InstantNewsTheme {
        NewsCard(
            News(
                title = "Un beau titre",
                description = "Lorem ipsum dolor sit amet consectetur. Lectus varius eu turpis vulputate libero sed.",
                url = "https://www.wired.com",
                urlToImage = "https://media.wired.com/photos/6696630a5d2d61e4805c3175/191:100/w_1280,c_limit/GettyImages-1979197796.jpg",
                source = "Wired"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        NewsCard(
            News(
                title = "Un beau titre",
                description = "Lorem ipsum dolor sit amet consectetur. Lectus varius eu turpis vulputate libero sed.",
                url = "https://www.wired.com",
                urlToImage = "https://media.wired.com/photos/6696630a5d2d61e4805c3175/191:100/w_1280,c_limit/GettyImages-1979197796.jpg",
                source = "Wired"
            )
        )
    }
}