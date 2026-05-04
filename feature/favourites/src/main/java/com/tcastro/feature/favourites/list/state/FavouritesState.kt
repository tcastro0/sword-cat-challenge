package com.tcastro.feature.favourites.list.state

import com.tcastro.feature.favourites.list.model.FavouriteUiModel


sealed interface FavouritesState {
    data object Loading : FavouritesState
    data class Success(
        val favourites: List<FavouriteUiModel>,
        val averageLifespan: Double
    ) : FavouritesState
    data class Error(val message: String) : FavouritesState
}