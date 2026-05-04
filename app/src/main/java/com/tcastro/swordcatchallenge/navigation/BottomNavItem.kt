package com.tcastro.swordcatchallenge.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: NavRoutes,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
