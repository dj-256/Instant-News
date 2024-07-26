package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InstantButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )
    }

}