package fr.joeldibasso.instantnews.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

@Composable
fun Onboarding(
    viewModel: NewsViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navController.currentDestination?.route ?: "onboarding/welcome"

    when (currentRoute.substringAfter("/")) {
        "scan_qr" -> viewModel.setLoginMethod(LoginMethod.QR_CODE)
        "login_error" -> viewModel.setLoginMethod(LoginMethod.TOKEN)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        OnboardingUpper(
            currentRoute = currentRoute,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        OnboardingLower(
            currentRoute = currentRoute,
            viewModel = viewModel,
            navController = navController,
            modifier = modifier.weight(1f)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun OnboardingPreview() {
    InstantNewsTheme {
        Surface {
            Onboarding(
                viewModel = viewModel(),
                navController = rememberNavController()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OnboardingPreviewDark() {
    InstantNewsTheme(darkTheme = true) {
        Surface {
            Onboarding(
                viewModel = viewModel(),
                navController = rememberNavController()
            )
        }
    }
}
