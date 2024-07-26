package fr.joeldibasso.instantnews

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.joeldibasso.instantnews.model.News
import fr.joeldibasso.instantnews.ui.composables.NewsDetails
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDetailsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val news1 = News(
        title = "Title 1",
        description = "Description 1",
        source = "Source 1",
        url = "https://example.com",
        urlToImage = "https://example.com/image1.jpg",
        content = "Content 1"
    )

    private val news2 = News(
        title = "Harris asks for 2024 support from women of color during an address at a historically Black sorority",
        description = null,
        source = "Google News",
        url = "https://news.google.com/rss/articles/CBMinwFBVV95cUxNVFBGbjhLaWphVWNqVnY2SGVTOE1adHFsTDlIUGxzMXp3aG1KT2pSdk9FTmEyYXRzWXV3d3p2V1VXNzlCSFlBUkdlVzVnNDVWVE1sRFJ2cW90WTRqRWdJU2VuNUExREx5Ulg2LUdlVE1CMm5sNnMyeWM1Y2xWVUluUkJBd05sZklFOGNjZEJ3TU9KbjFpa0FCWmR6cVY5Vmc?oc=5",
        urlToImage = null,
        content = null
    )

    private val news3 = News(
        title = "Lady Gaga Set to Perform at 2024 Paris Olympics Opening Ceremony",
        description = "The Olympic Games officially kick off on Friday.",
        source = "Hollywood Reporter",
        url = "http://www.hollywoodreporter.com/news/general-news/lady-gaga-perform-olympics-2024-opening-ceremony-1235957365/",
        urlToImage = "https://www.hollywoodreporter.com/wp-content/uploads/2024/07/Lady-Gaga-getty-H-2024.jpg?w=1296&h=730&crop=1",
        content = "Lady Gaga is set to perform at the opening ceremony of the 2024 Paris Olympic Games this Friday, The Hollywood Reporter has learned.\r\nThis comes after fans speculated the Shallow singer was set to ta… [+1647 chars]"
    )

    @Test
    fun displaySimpleNews() {
        composeTestRule.setContent {
            NewsDetails(news1)
        }
        composeTestRule.onNodeWithText("Title 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Source 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Content 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Read more").assertIsDisplayed()
    }

    @Test
    fun displayNewsWithoutDescriptionImageOrContent() {
        composeTestRule.setContent {
            NewsDetails(news2)
        }
        composeTestRule.onNodeWithText("Harris asks for 2024", true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Google News", true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("news_description").assertDoesNotExist()
        composeTestRule.onNodeWithTag("news_image").assertDoesNotExist()
        composeTestRule.onNodeWithTag("news_content").assertDoesNotExist()
        composeTestRule.onNodeWithText("Read more").assertIsDisplayed()
    }

    @Test
    fun displayNewsWithEverything() {
        composeTestRule.setContent {
            NewsDetails(news3)
        }
        composeTestRule.onNodeWithText("Lady Gaga Set to Perform at 2024 Paris Olympics Opening Ceremony")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("The Olympic Games officially kick off on Friday.")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Hollywood Reporter").assertIsDisplayed()
        composeTestRule.onNodeWithTag("news_image").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Lady Gaga is set to perform at the opening ceremony of the 2024 Paris Olympic Games this Friday, The Hollywood Reporter has learned.",
            true
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText("Read more").assertIsDisplayed()
    }
}