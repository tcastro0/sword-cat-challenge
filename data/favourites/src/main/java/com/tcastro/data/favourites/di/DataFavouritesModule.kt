package com.tcastro.data.favourites.di

import com.tcastro.data.favourites.repository.FavouritesRepositoryImpl
import com.tcastro.domain.favourites.repository.FavouritesRepository
import org.koin.dsl.module


fun dataFavouritesModule() = module {
    single<FavouritesRepository> {
        FavouritesRepositoryImpl(favouriteDao = get())
    }
}