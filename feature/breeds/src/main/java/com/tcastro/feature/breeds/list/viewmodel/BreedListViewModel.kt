package com.tcastro.feature.breeds.list.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tcastro.domain.breeds.usecase.GetBreedsUseCase
import com.tcastro.domain.breeds.usecase.SearchBreedUseCase
import com.tcastro.domain.favourites.model.Favourite
import com.tcastro.domain.favourites.usecase.GetFavouriteIdsUseCase
import com.tcastro.domain.favourites.usecase.SetFavouriteUseCase
import com.tcastro.feature.breeds.list.mapper.toUIModel
import com.tcastro.feature.breeds.list.model.BreedUIModel
import com.tcastro.feature.breeds.list.state.BreedListState
import com.tcastro.feature.breeds.list.state.SearchState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BreedListViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getBreedsUseCase: GetBreedsUseCase,
    private val searchBreedUseCase: SearchBreedUseCase,
    private val setFavouriteUseCase: SetFavouriteUseCase,
    private val getFavouriteIdsUseCase: GetFavouriteIdsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedListState>(BreedListState())
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val breeds: Flow<PagingData<BreedUIModel>> = getFavouriteIdsUseCase()
        .flatMapLatest { favouriteIds ->
            getBreedsUseCase().map { pagingData ->
                pagingData.map { breed ->
                    breed.copy(isFavourite = breed.id in favouriteIds).toUIModel()
                }
            }
        }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch(dispatcher) {
            uiState
                .map { it.searchQuery }
                .debounce(300L)
                .distinctUntilChanged()
                .filter { it.isNotEmpty() }
                .collect { query ->
                    searchBreeds(query)
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isEmpty()) {
            _uiState.update { it.copy(searchState = SearchState.Idle) }
        }
    }

    private fun searchBreeds(query: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(searchState = SearchState.Loading) }
            searchBreedUseCase(query)
                .catch {
                    _uiState.update { state ->
                        state.copy(searchState = SearchState.Error(it.message ?: "Unknown error"))
                    }
                }
                .collectLatest { breeds ->
                    Log.d("collectLatest", "breeds - ${breeds.count()}")
                    _uiState.update { state ->
                        state.copy(searchState = SearchState.Success(breeds.toUIModel()))
                    }
                }
        }
    }

    fun toggleFavourite(breed: BreedUIModel) {
        viewModelScope.launch(dispatcher) {
            setFavouriteUseCase(
                Favourite(
                    breedId = breed.id,
                    breedName = breed.name,
                    breedImageUrl = breed.imageUrl,
                    breedLifeSpan = breed.lifespan ?:"",
                    breedOrigin = breed.origin ?:""
                )
            )
        }
    }


}