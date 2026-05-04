package com.tcastro.data.core.di

import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcastro.data.core.database.SwordCatsDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


fun dataCoreModule(baseUrl: String, apiKey: String) = module {

    single(named("baseUrl")) { baseUrl }
    single(named("apiKey")) { apiKey }

    //OkHttpClient provider
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", get(named("apiKey")))
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    //Moshi provider
    single {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    //Retrofit provider
    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    //Database provider
    single {
        Room.databaseBuilder(
            androidContext(),
            SwordCatsDatabase::class.java,
            "sword_cats_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    // DAOs provider
    single { get<SwordCatsDatabase>().breedDao() }
    single { get<SwordCatsDatabase>().remoteKeyDao() }
    single { get<SwordCatsDatabase>().favouriteDao() }

}
