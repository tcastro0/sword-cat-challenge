package com.tcastro.data.breeds.di

import com.tcastro.data.breeds.remote.service.BreedsService
import com.tcastro.data.breeds.repository.BreedsRepositoryImpl
import com.tcastro.domain.breeds.repository.BreedsRepository
import org.koin.dsl.module
import retrofit2.Retrofit


fun dataBreedsModule() = module {
    single<BreedsService> {
        get<Retrofit>().create(BreedsService::class.java)
    }

    single<BreedsRepository> {
        BreedsRepositoryImpl(
            service = get(),
            database = get(),
            breedDao = get(),
            remoteKeyDao = get()
        )
    }
}