package com.tcastro.feature.breeds.list.state

import com.tcastro.feature.breeds.list.model.BreedUIModel


data class BreedListState(
    val searchQuery: String = "",
    val searchState: SearchState = SearchState.Idle
)

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Success(val breeds: List<BreedUIModel>) : SearchState
    data class Error(val message: String) : SearchState
}