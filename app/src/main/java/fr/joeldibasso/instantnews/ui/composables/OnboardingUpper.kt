package fr.joeldibasso.instantnews.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@Composable
fun OnboardingUpper(currentRoute: String, modifier: Modifier = Modifier) {
    Log.d("OnboardingUpper", "currentRoute: $currentRoute")
    val message: String = when (currentRoute.substringAfter("/")) {
        "welcome" -> {
            "Welcome, please choose a login method."
        }

        "login_error" -> {
            "We couldn't log you in. Please try again."
        }

        "type_token" -> {
            "Please enter your token."
        }

        else -> return
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Instant News",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingUpperPreview() {
    InstantNewsTheme {
        Surface {
            OnboardingUpper(currentRoute = "onboarding/welcome")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingUpperPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        Surface {
            OnboardingUpper(currentRoute = "onboarding/welcome")
        }
    }
}