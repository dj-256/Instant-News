package fr.joeldibasso.instantnews.ui.composables

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import fr.joeldibasso.instantnews.ui.NewsViewModel

/**
 * QrCodeScanScreen is the screen where the user can scan a QR code to log in.
 * @param modifier The modifier for the screen.
 * @param viewModel The NewsViewModel used to interact with the app state.
 * @param navController The navigation controller that manages the navigation.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrCodeScanScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel,
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()
    val isLoggedIn = state.isLoggedIn
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    // Request camera permission on first launch
    LaunchedEffect(true) {
        cameraPermissionState.run { launchPermissionRequest() }
    }

    val context = LocalContext.current
    // Save token to preferences and navigate to app screen when logged in
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            val prefs = context.getSharedPreferences("instant_news", 0)
            with(prefs.edit()) {
                putString("token", state.token)
                apply()
            }
            navController.navigate("app")
        }
    }

    // Navigate to login error screen if login fails
    LaunchedEffect(state.isLoginError) {
        if (state.isLoginError) {
            navController.navigate("onboarding/login_error")
        }
    }

    if (cameraPermissionState.status.isGranted) {
        Text("Camera permission Granted")
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                "The camera is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Camera permission required for this feature to be available. Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (cameraPermissionState.status.isGranted) {
            CameraScreen { qrCode ->
                viewModel.getTopNews(qrCode)
            }
        }
    }
}