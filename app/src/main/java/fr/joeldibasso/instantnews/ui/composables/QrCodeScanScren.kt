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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import fr.joeldibasso.instantnews.ui.NewsViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrCodeScanScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {
    val state by viewModel.uiState.collectAsState()
    val isLoggedIn = state.isLoggedIn
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    LaunchedEffect(key1 = true) {
        cameraPermissionState.run { launchPermissionRequest() }
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = isLoggedIn) {
        if (isLoggedIn) {
            val prefs = context.getSharedPreferences("instant_news", 0)
            with(prefs.edit()) {
                putString("token", state.token)
                apply()
            }
            navController.navigate("top_news")
        }
    }

    if (cameraPermissionState.status.isGranted) {
        Text("Camera permission Granted")
    } else {
        Column {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The camera is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
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
                viewModel.checkToken(qrCode)
            }
        }
    }
}