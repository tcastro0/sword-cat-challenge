package com.tcastro.feature.breeds.list.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcastro.domain.breeds.usecase.GetBreedsUseCase
import com.tcastro.feature.breeds.list.mapper.toUIModel
import com.tcastro.feature.breeds.list.state.BreedListState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BreedListViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getBreedsUseCase: GetBreedsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedListState>(BreedListState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadBreeds()
    }


    private fun loadBreeds(){
        viewModelScope.launch(dispatcher) {
            getBreedsUseCase().catch {
                Log.d("load data", "error ${it.message}")
                _uiState.update { BreedListState.Error("problem as occurred") }
            }.collectLatest {
                val items = it.toUIModel()
                _uiState.update {
                    BreedListState.Success(items)
                }
            }
        }
    }


}