package com.tcastro.data.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


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
            .addConverterFactory(get())
            .build()
    }

}
