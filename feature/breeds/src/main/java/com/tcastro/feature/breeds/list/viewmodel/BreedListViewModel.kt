package com.tcastro.feature.breeds.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tcastro.domain.breeds.usecase.GetBreedsUseCase
import com.tcastro.feature.breeds.list.mapper.toUIModel
import com.tcastro.feature.breeds.list.model.BreedUIModel
import com.tcastro.feature.breeds.list.state.BreedListState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class BreedListViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getBreedsUseCase: GetBreedsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedListState>(BreedListState.Loading)
    val uiState = _uiState.asStateFlow()

    val breeds: Flow<PagingData<BreedUIModel>> = getBreedsUseCase()
        .map { pagingData -> pagingData.map { it.toUIModel() } }
        .cachedIn(viewModelScope)


    init {

    }





}