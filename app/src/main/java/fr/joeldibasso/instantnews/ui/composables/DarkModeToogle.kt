package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.joeldibasso.instantnews.R

@Composable
fun DarkModeToggle(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = true,
    onToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        AnimatedContent(
            targetState = isDarkMode,
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
            checked = isDarkMode,
            onCheckedChange = { onToggle(it) })
        Spacer(modifier = Modifier.width(4.dp))
    }
}