package com.tcastro.data.breeds.repository

import app.cash.turbine.test
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tcastro.data.breeds.remote.service.BreedsService
import com.tcastro.data.breeds.utils.TestUtils.readJson
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class BreedsRepositoryIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: BreedsService
    private lateinit var repo: BreedsRepositoryImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

       val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        service = retrofit.create(BreedsService::class.java)

        repo = BreedsRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun `getBreeds returns mapped domain models`() = runTest {
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(readJson("breeds_response.json"))
                .build()
        )

        repo.getBreeds().test {
            val breeds = awaitItem()
            assert(breeds.isNotEmpty())
            assert(breeds.first().id == "abys")
            awaitComplete()
        }
    }


    @Test
    fun `getBreedById returns correct breed`() = runTest {
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(readJson("breed_by_id_response.json"))
                .build()
        )

        repo.getBreedById("aege").test {
            val breed = awaitItem()
            assert(breed!=null)
            assert(breed?.id == "aege")
            awaitComplete()
        }
    }



    @Test
    fun `breed search returns collection`() = runTest {
        mockWebServer.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(readJson("breeds_search_response.json"))
                .build()
        )

        repo.searchBreeds("air").test {
            val breeds = awaitItem()
            assert(breeds.isNotEmpty())
            assert(breeds.first().name.contains("air"))
            awaitComplete()
        }
    }



}