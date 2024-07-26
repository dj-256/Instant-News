package fr.joeldibasso.instantnews

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.joeldibasso.instantnews.ui.composables.NewsAppState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(
                navController = navController,
                state = NewsAppState(),
                startDestination = "onboarding/welcome"
            )
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Welcome, please choose a login method")
            .assertIsDisplayed()
    }
}