package com.tcastro.swordcatchallenge.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavRoutes : NavKey {

    @Serializable
    data object BreedsList : NavRoutes

    @Serializable
    data class BreedDetail(val breedId: String) : NavRoutes

    @Serializable
    data object Favourites : NavRoutes

}