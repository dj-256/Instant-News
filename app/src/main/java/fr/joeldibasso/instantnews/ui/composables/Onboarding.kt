package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@Composable
fun Onboarding(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    text = "Welcome, please choose a login method",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Button(onClick = { navController.navigate("onboarding/scan_qr") }) {
                Text(
                    "Scan QR code",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(
                    "Log in with token",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OnboardingPreview() {
    InstantNewsTheme {
        Onboarding()
    }
}

@Composable
@Preview(showBackground = true)
fun OnboardingPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        Onboarding()
    }
}