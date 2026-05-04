package com.tcastro.swordcatchallenge.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.tcastro.core.ui.theme.SwordCatChallengeTheme



val bottomNavItems = listOf(
    BottomNavItem(
        route = NavRoutes.BreedsList,
        label = "Cats list",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Filled.Person
    ),
    BottomNavItem(
        route = NavRoutes.Favourites,
        label = "Favourites",
        selectedIcon = Icons.Filled.FavoriteBorder,
        unselectedIcon = Icons.Filled.FavoriteBorder
    )
)

@Composable
fun NavigationScaffold() {
    val backStack = rememberNavBackStack(NavRoutes.BreedsList)

    val currentRoute by remember {
        derivedStateOf { backStack.lastOrNull() }
    }

    val showBottomBar by remember {
        derivedStateOf {
            currentRoute is NavRoutes.BreedsList || currentRoute is NavRoutes.Favourites
        }
    }

    SwordCatChallengeTheme() {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBottomBar(
                        backStack = backStack,
                        currentRoute = currentRoute
                    )
                }
            }
        ) { paddingValues ->
            AppNavigation(
                backStack = backStack,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}