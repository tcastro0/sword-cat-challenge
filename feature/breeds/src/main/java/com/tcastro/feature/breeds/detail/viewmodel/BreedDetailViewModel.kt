package com.tcastro.feature.breeds.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcastro.domain.breeds.usecase.GetBreedDetailUseCase
import com.tcastro.feature.breeds.detail.mapper.toDetailUiModel
import com.tcastro.feature.breeds.detail.state.BreedDetailState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BreedDetailViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getBreedDetailUseCase: GetBreedDetailUseCase,
    private val breedId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedDetailState>(BreedDetailState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadDetails()
    }


    private fun loadDetails() {
        viewModelScope.launch(dispatcher) {
            _uiState.update { BreedDetailState.Loading }

            getBreedDetailUseCase(breedId)
                .catch { e ->
                    _uiState.update {
                        BreedDetailState.Error(
                            message = "Breed not found",
                            detail = e.message ?: "Unknown error"
                        )
                    }
                }
                .collectLatest { breed ->
                    breed?.let {
                        _uiState.update { BreedDetailState.Success(breed.toDetailUiModel()) }
                    } ?: run {
                        _uiState.update { BreedDetailState.Error("Breed not found") }
                    }
                }
        }
    }

}