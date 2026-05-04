package com.tcastro.feature.breeds.detail.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcastro.domain.breeds.usecase.GetBreedDetailUseCase
import com.tcastro.domain.favourites.model.Favourite
import com.tcastro.domain.favourites.usecase.GetFavouriteIdsUseCase
import com.tcastro.domain.favourites.usecase.SetFavouriteUseCase
import com.tcastro.feature.breeds.detail.mapper.toDetailUiModel
import com.tcastro.feature.breeds.detail.state.BreedDetailState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BreedDetailViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getBreedDetailUseCase: GetBreedDetailUseCase,
    private val breedId: String,
    private val setFavouriteUseCase: SetFavouriteUseCase,
    private val getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedDetailState>(BreedDetailState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadDetails()
    }


    private fun loadDetails() {
        viewModelScope.launch(dispatcher) {
            _uiState.update { BreedDetailState.Loading }

            combine(
                getBreedDetailUseCase(breedId),
                getFavouriteIdsUseCase()
            ) { details, favouriteIds ->
                if (details != null) {
                    BreedDetailState.Success(
                        details.copy(isFavourite = details.id in favouriteIds)
                            .toDetailUiModel()
                    )
                } else {
                    BreedDetailState.Error(
                        message = "Breed not found"
                    )
                }
            }.catch { e ->
                _uiState.update {
                    BreedDetailState.Error(
                        message = "Breed not found",
                        detail = e.message ?: "Unknown error"
                    )
                }
            }.collect {
                _uiState.value = it
            }

        }
    }

    fun toggleFavourite() {
        val current = _uiState.value as? BreedDetailState.Success ?: return
        val breed = current.breed

        viewModelScope.launch(dispatcher) {
            setFavouriteUseCase(
                Favourite(
                    breedId = breed.id,
                    breedName = breed.name,
                    breedImageUrl = breed.imageUrl,
                    breedLifeSpan = breed.lifespan ?: "",
                    breedOrigin = breed.origin
                )
            )
        }
    }

}