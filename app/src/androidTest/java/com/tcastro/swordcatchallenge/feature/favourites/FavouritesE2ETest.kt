package com.tcastro.swordcatchallenge.feature.favourites


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tcastro.swordcatchallenge.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FavouritesE2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun navigation_switchToFavourites_showsFavouritesScreen() {
        composeRule
            .onNodeWithText("Favourites")
            .performClick()

        composeRule
            .onNodeWithText("Favourites")
            .assertIsDisplayed()
    }


    @Test
    fun favourites_addBreed_appearsInFavouritesScreen() {
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule
                .onAllNodesWithContentDescription("Add to favourites")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule
            .onAllNodesWithContentDescription("Add to favourites")
            .get(0)
            .performClick()

        composeRule
            .onNodeWithText("Favourites")
            .performClick()

        composeRule
            .onNodeWithText("AVERAGE LIFESPAN")
            .assertIsDisplayed()
    }
}