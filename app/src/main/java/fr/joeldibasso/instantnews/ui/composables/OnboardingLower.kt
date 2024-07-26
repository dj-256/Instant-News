package fr.joeldibasso.instantnews.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.joeldibasso.instantnews.ui.NewsViewModel
import fr.joeldibasso.instantnews.ui.theme.InstantNewsTheme

/**
 * OnboardingLower is the lower part of the onboarding screen.
 * It contains the buttons to navigate between the different onboarding screens.
 * @param currentRoute The current route of the onboarding screen.
 * @param navController The navigation controller that manages the navigation.
 * @param viewModel The NewsViewModel used to interact with the app state.
 * @param modifier The modifier for this composable.
 */
@Composable
fun OnboardingLower(
    currentRoute: String,
    navController: NavController,
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    LaunchedEffect(state.isLoginError) {
        if (currentRoute != "onboarding/login_error" && state.isLoginError) {
            viewModel.updateToken(null)
            navController.navigate("onboarding/login_error") {
                popUpTo("onboarding/welcome") { inclusive = true }
            }
        }

    }

    LaunchedEffect(state.isLoggedIn) {
        if (currentRoute == "onboarding/type_token" && !state.isLoginError && state.isLoggedIn) {
            val prefs = context.getSharedPreferences("instant_news", 0)
            with(prefs.edit()) {
                putString("token", state.token)
                apply()
            }
            navController.navigate("app") {
                popUpTo("onboarding/welcome") { inclusive = true }
            }
        }
    }

    LaunchedEffect(null) {
        if (currentRoute == "onboarding/type_token") {
            focusRequester.requestFocus()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        when (currentRoute.substringAfter("/")) {
            // The "welcome" screen displays two buttons to log in with a token or a QR code
            "welcome" -> {
                InstantButton(
                    onClick = {
                        navController.navigate("onboarding/scan_qr")
                    },
                    text = "Scan QR code"
                )
                Spacer(modifier = Modifier.height(20.dp))
                InstantButton(
                    onClick = {
                        navController.navigate("onboarding/type_token")
                    }, text = "Log in with token"
                )
            }

            // The "login_error" screen displays two buttons to try again with the same method or switch to the other method
            "login_error" -> {
                InstantButton(
                    onClick = {
                        viewModel.clearLoginError()
                        // The "Try again" button navigates to the screen matching the login method
                        navController.navigate(
                            route = when (state.loginMethod) {
                                LoginMethod.TOKEN -> "onboarding/type_token"
                                LoginMethod.QR_CODE -> "onboarding/scan_qr"
                            }
                        )
                    },
                    text = "Try again"
                )
                Spacer(modifier = Modifier.height(20.dp))
                InstantButton(
                    onClick = {
                        viewModel.clearLoginError()
                        // The second button navigates to the screen matching the other login method
                        navController.navigate(
                            route = when (state.loginMethod) {
                                LoginMethod.TOKEN -> "onboarding/scan_qr"
                                LoginMethod.QR_CODE -> "onboarding/type_token"
                            }
                        )
                    }, text = when (state.loginMethod) {
                        LoginMethod.TOKEN -> "Login with QR code"
                        LoginMethod.QR_CODE -> "Type API key"
                    }
                )
            }

            // The "type_token" screen displays a text field to enter the token and a button to log in
            "type_token" -> {
                OutlinedTextField(
                    value = state.token ?: "",
                    onValueChange = {
                        viewModel.updateToken(it)
                    },
                    label = { Text("API key") },
                    placeholder = { Text("Enter your API key") },
                    shape = RoundedCornerShape(24.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Send
                    ),
                    // The "onSend" action is triggered when the user presses the "Send" button on the keyboard
                    keyboardActions = KeyboardActions(onSend = {
                        Log.d("OnboardingLower", "Token: ${state.token}")
                        viewModel.getTopNews(state.token ?: "")
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                )
                Spacer(modifier = Modifier.height(40.dp))
                InstantButton(
                    onClick = {
                        viewModel.getTopNews(state.token ?: "")
                    },
                    text = "Log in"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLowerPreviewWelcome() {
    InstantNewsTheme {
        Surface {
            OnboardingLower(
                currentRoute = "onboarding/welcome",
                navController = rememberNavController(),
                viewModel = viewModel()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLowerPreviewWelcomeDark() {
    InstantNewsTheme(darkTheme = true) {
        Surface {
            OnboardingLower(
                currentRoute = "onboarding/welcome",
                navController = rememberNavController(),
                viewModel = viewModel()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLowerPreviewLoginError() {
    InstantNewsTheme {
        Surface {
            OnboardingLower(
                currentRoute = "onboarding/login_error",
                navController = rememberNavController(),
                viewModel = viewModel()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLowerPreviewLoginErrorDark() {
    InstantNewsTheme(darkTheme = true) {
        Surface {
            OnboardingLower(
                currentRoute = "onboarding/login_error",
                navController = rememberNavController(),
                viewModel = viewModel()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLowerPreviewTypeToken() {
    InstantNewsTheme {
        Surface {
            OnboardingLower(
                currentRoute = "onboarding/type_token",
                navController = rememberNavController(),
                viewModel = viewModel()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingLowerPreviewTypeTokenDark() {
    InstantNewsTheme(darkTheme = true) {
        Surface {
            OnboardingLower(
                currentRoute = "onboarding/type_token",
                navController = rememberNavController(),
                viewModel = viewModel()
            )
        }
    }
}