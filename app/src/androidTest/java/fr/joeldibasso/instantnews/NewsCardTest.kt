package fr.joeldibasso.instantnews

import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.joeldibasso.instantnews.model.News
import fr.joeldibasso.instantnews.ui.composables.NewsCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val news1 = News(
        title = "Title 1",
        description = "Description 1",
        imageUrl = "https://example.com/image1.jpg"
    )

    private val news2 = News(
        title = "Title So Big You Will Definitely Need At Least Two Lines To Display It",
        description = "Lorem ipsum odor amet, consectetuer adipiscing elit. Mus risus pretium iaculis eu purus. Vehicula accumsan mus lacus viverra et sed sociosqu habitant. Convallis ligula fringilla suspendisse commodo inceptos eget. Feugiat praesent ac non eros bibendum. Ullamcorper tortor velit, tempus lorem cras curae amet. Posuere ligula libero nisi hendrerit finibus est. Bibendum pulvinar quis; elementum vehicula tristique purus senectus porttitor. Faucibus non commodo sociosqu dapibus aliquet, vivamus auctor fermentum eleifend? Adipiscing metus dapibus velit mus facilisis sem erat blandit. Vel pharetra aenean hac integer molestie nascetur. Accumsan aliquam ullamcorper quisque ut; scelerisque erat magna nisi. Luctus gravida elementum integer donec sodales. Curae arcu torquent purus suscipit; suscipit egestas aliquet. Dolor per metus aliquam volutpat ex. Nisi iaculis et vivamus ullamcorper sem sapien turpis orci accumsan? Phasellus duis orci vivamus quam dui integer. Class ligula leo ipsum velit; nascetur efficitur.",
        imageUrl = "https://example.com/image2.jpg"
    )

    @Test
    fun titleIsDisplayed() {
        composeTestRule.setContent {
            NewsCard(news1)
        }
        composeTestRule.onNodeWithText("Title 1").assertExists()
    }

    @Test
    fun descriptionIsDisplayed() {
        composeTestRule.setContent {
            NewsCard(news1)
        }
        composeTestRule.onNodeWithText("Description 1").assertExists()
    }

    @Test
    fun bigTitleOnTwoLines() {
        composeTestRule.setContent {
            NewsCard(news2)
        }
        composeTestRule.onNodeWithTag("news_title").assertHeightIsAtLeast(48.dp)
    }
}