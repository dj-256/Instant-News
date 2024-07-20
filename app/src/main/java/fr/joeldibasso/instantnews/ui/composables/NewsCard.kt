package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme
import kotlin.math.truncate

@Composable
fun NewsCard(title: String, description: String, imageUrl: String, modifier: Modifier = Modifier) {
    Card(
        modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardColors(
            containerColor = Color(0xFF212035),
            contentColor = CardDefaults.cardColors().contentColor,
            disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
            disabledContentColor = CardDefaults.cardColors().disabledContentColor,
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(12.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFE6E6EF),
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFFA8A8C7),
                    modifier = Modifier.height(30.dp),
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
            title = "Un beau titre",
            description = "Lorem ipsum dolor sit amet consectetur. Lectus varius eu turpis vulputate libero sed.",
            imageUrl = "https://media.wired.com/photos/6696630a5d2d61e4805c3175/191:100/w_1280,c_limit/GettyImages-1979197796.jpg"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        NewsCard(
            title = "Un beau titre",
            description = "Lorem ipsum dolor sit amet consectetur. Lectus varius eu turpis vulputate libero sed.",
            imageUrl = "https://media.wired.com/photos/6696630a5d2d61e4805c3175/191:100/w_1280,c_limit/GettyImages-1979197796.jpg"
        )
    }
}