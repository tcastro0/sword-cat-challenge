package com.tcastro.feature.breeds.list.state

import com.tcastro.feature.breeds.list.model.BreedUIModel


sealed interface BreedListState {
    data object Loading : BreedListState
    data class Success(val breeds: List<BreedUIModel>) : BreedListState
    data class Error(val message: String) : BreedListState
}