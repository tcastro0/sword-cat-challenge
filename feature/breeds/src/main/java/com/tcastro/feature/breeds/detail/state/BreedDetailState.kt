package com.tcastro.feature.breeds.detail.state

import com.tcastro.feature.breeds.detail.model.BreedDetailUiModel


sealed interface BreedDetailState {
    data object Loading : BreedDetailState
    data class Success(val breed: BreedDetailUiModel) : BreedDetailState
    data class Error(val message: String,val detail:String?=null) : BreedDetailState
}