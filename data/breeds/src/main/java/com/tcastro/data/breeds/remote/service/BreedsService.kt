package com.tcastro.data.breeds.remote.service

import com.tcastro.data.breeds.remote.model.BreedDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreedsService {

    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int = 20,
        @Query("page") page: Int = 0
    ): List<BreedDto>

    @GET("breeds/{breed_id}")
    suspend fun getBreedById(
        @Path("breed_id") breedId: String
    ): BreedDto

    @GET("breeds/search")
    suspend fun searchBreeds(
        @Query("q") query: String
    ): List<BreedDto>
}