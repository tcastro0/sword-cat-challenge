package com.tcastro.feature.breeds.di

import com.tcastro.domain.breeds.usecase.GetBreedsUseCase
import com.tcastro.feature.breeds.list.viewmodel.BreedListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun featureBreedsModule() = module {
    viewModel {
        BreedListViewModel(
            dispatcher = Dispatchers.IO,
            getBreedsUseCase = GetBreedsUseCase(get()),
        )
    }
}