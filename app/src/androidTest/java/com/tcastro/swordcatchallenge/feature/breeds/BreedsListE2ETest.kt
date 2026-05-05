package com.tcastro.swordcatchallenge.feature.breeds

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tcastro.swordcatchallenge.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin


@RunWith(AndroidJUnit4::class)
class BreedsListE2ETest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(composeRule.activity)
                modules()
            }
        }
    }

    @Test
    fun breedsList_isDisplayedOnLaunch() {
        composeRule
            .onNodeWithText("Find your cat")
            .assertIsDisplayed()
    }

    @Test
    fun breedsList_searchBarIsDisplayed() {
        composeRule
            .onNodeWithText("Search your breed...")
            .assertIsDisplayed()
    }

    @Test
    fun breedsList_searchBreed_showsResults() {
        composeRule
            .onNodeWithText("Search your breed...")
            .performTextInput("Ragdoll")

        // wait for debounce + network
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule
                .onAllNodesWithText("Ragdoll")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule
            .onNodeWithText("Ragdoll")
            .assertIsDisplayed()
    }

    @Test
    fun breedsList_toggleFavourite_updatesIcon() {
        composeRule.waitForIdle()

        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule
                .onAllNodesWithContentDescription("Add to favourites")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule
            .onAllNodesWithContentDescription("Add to favourites")
            .get(0)
            .performClick()

        composeRule.waitForIdle()

        composeRule
            .onAllNodesWithContentDescription("Remove from favourites")
            .get(0)
            .assertIsDisplayed()
    }
}