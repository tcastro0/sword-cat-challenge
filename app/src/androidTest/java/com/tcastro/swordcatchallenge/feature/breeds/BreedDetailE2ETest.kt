package com.tcastro.swordcatchallenge.feature.breeds

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tcastro.swordcatchallenge.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BreedDetailE2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun breedsList_tapBreed_navigatesToDetail() {
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule
                .onAllNodesWithContentDescription("Abyssinian")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule
            .onNodeWithContentDescription("Abyssinian")
            .assertIsDisplayed()
    }


    @Test
    fun detail_toggleFavourite_updatesButton() {

        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule
                .onAllNodesWithContentDescription("Abyssinian")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule
            .onNodeWithContentDescription("Abyssinian")
            .performClick()

        // toggle favourite
        composeRule
            .onNodeWithText("Add to favourites")
            .assertIsDisplayed()
            .performClick()

        // button should now show remove
        composeRule
            .onNodeWithText("Remove from favourites")
            .assertIsDisplayed()
    }
}