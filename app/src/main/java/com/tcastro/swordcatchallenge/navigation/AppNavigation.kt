package com.tcastro.swordcatchallenge.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.tcastro.feature.breeds.detail.screen.BreedDetailScreen
import com.tcastro.feature.breeds.list.screen.BreedListScreen


@Composable
fun AppNavigation(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { route ->
            when (route) {
                is NavRoutes.BreedsList -> NavEntry(route) {
                    BreedListScreen(
                        onItemClick = { breedId ->
                            backStack.add(NavRoutes.BreedDetail(breedId))
                        }
                    )
                }

                is NavRoutes.BreedDetail -> NavEntry(route) {
                    BreedDetailScreen(
                        onBack = { backStack.removeLastOrNull() }
                    )
                }

                is NavRoutes.Favourites -> NavEntry(route) {
                    Text("Favourites")
                }

                else -> NavEntry(route) {}
            }
        }
    )
}