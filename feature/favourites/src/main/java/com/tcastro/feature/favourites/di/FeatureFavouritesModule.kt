package com.tcastro.feature.favourites.di

import com.tcastro.domain.favourites.usecase.GetAverageLifespanUseCase
import com.tcastro.domain.favourites.usecase.GetFavouritesUseCase
import com.tcastro.domain.favourites.usecase.SetFavouriteUseCase
import com.tcastro.feature.favourites.list.viewmodel.FavouritesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun featureFavouritesModule() = module {
    viewModel {
        FavouritesViewModel(
            getFavouritesUseCase = GetFavouritesUseCase(get()),
            setFavouriteUseCase = SetFavouriteUseCase(get()),
            getAverageLifespanUseCase = GetAverageLifespanUseCase(get()),
            dispatcher = Dispatchers.IO
        )
    }
}