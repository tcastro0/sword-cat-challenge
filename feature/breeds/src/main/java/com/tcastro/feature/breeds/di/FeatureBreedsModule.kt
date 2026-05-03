package com.tcastro.feature.breeds.di

import com.tcastro.domain.breeds.usecase.GetBreedDetailUseCase
import com.tcastro.domain.breeds.usecase.GetBreedsUseCase
import com.tcastro.domain.breeds.usecase.SearchBreedUseCase
import com.tcastro.feature.breeds.detail.viewmodel.BreedDetailViewModel
import com.tcastro.feature.breeds.list.viewmodel.BreedListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun featureBreedsModule() = module {
    viewModel {
        BreedListViewModel(
            dispatcher = Dispatchers.IO,
            getBreedsUseCase = GetBreedsUseCase(get()),
            searchBreedUseCase = SearchBreedUseCase(get()),
        )
    }

    viewModel {
        BreedDetailViewModel(
            dispatcher = Dispatchers.IO,
            getBreedDetailUseCase = GetBreedDetailUseCase(get()),
        )
    }
}