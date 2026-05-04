package com.tcastro.feature.favourites.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcastro.domain.favourites.usecase.GetAverageLifespanUseCase
import com.tcastro.domain.favourites.usecase.GetFavouritesUseCase
import com.tcastro.domain.favourites.usecase.SetFavouriteUseCase
import com.tcastro.feature.favourites.list.mapper.toDomain
import com.tcastro.feature.favourites.list.mapper.toUiModel
import com.tcastro.feature.favourites.list.model.FavouriteUiModel
import com.tcastro.feature.favourites.list.state.FavouritesState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class FavouritesViewModel(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val setFavouriteUseCase: SetFavouriteUseCase,
    private val getAverageLifespanUseCase: GetAverageLifespanUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesState>(FavouritesState.Loading)
    val uiState: StateFlow<FavouritesState> = _uiState.asStateFlow()

    init {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch(dispatcher) {
            combine(
                getFavouritesUseCase(),
                getAverageLifespanUseCase()
            ) { favourites, averageLifespan ->
                FavouritesState.Success(
                    favourites = favourites.map { it.toUiModel() },
                    averageLifespan = averageLifespan
                )
            }.catch { e ->
                _uiState.update { FavouritesState.Error(e.message ?: "Unknown error") }
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun removeFavourite(favourite: FavouriteUiModel) {
        viewModelScope.launch(dispatcher) {
            setFavouriteUseCase(favourite.toDomain())
        }
    }
}