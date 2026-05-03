package com.tcastro.data.breeds.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tcastro.data.breeds.mapper.toDomain
import com.tcastro.data.breeds.mapper.toEntity
import com.tcastro.data.breeds.mediator.BreedsMediator
import com.tcastro.data.breeds.remote.service.BreedsService
import com.tcastro.data.core.database.SwordCatsDatabase
import com.tcastro.data.core.database.dao.BreedDao
import com.tcastro.data.core.database.dao.RemoteKeyDao
import com.tcastro.domain.breeds.model.Breed
import com.tcastro.domain.breeds.repository.BreedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BreedsRepositoryImpl(
    private val service: BreedsService,
    private val database: SwordCatsDatabase,
    private val breedDao: BreedDao,
    private val remoteKeyDao: RemoteKeyDao
) : BreedsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getBreeds(): Flow<PagingData<Breed>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            remoteMediator = BreedsMediator(
                apiService = service,
                database = database,
                breedDao = breedDao,
                remoteKeyDao = remoteKeyDao
            ),
            pagingSourceFactory = { breedDao.getBreedsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchBreeds(query: String): Flow<List<Breed>> = flow {
        try {
            val remoteResults = service.searchBreeds(query)
            val entities = remoteResults.map { it.toEntity() }
            breedDao.insertAll(entities)

            emit(remoteResults.map { it.toDomain() })
        } catch (e: Exception) {
            breedDao.searchBreeds(query)
                .map { entities -> entities.map { it.toDomain() } }
                .collect { emit(it) }
        }
    }

    override suspend fun getBreedById(id: String): Flow<Breed?> = flow {
        val breed = service.getBreedById(id).toDomain()
        emit(breed)
    }
}