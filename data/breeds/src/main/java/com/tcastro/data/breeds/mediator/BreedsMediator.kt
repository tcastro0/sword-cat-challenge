package com.tcastro.data.breeds.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tcastro.data.breeds.mapper.toEntity
import com.tcastro.data.breeds.remote.service.BreedsService
import com.tcastro.data.core.database.SwordCatsDatabase
import com.tcastro.data.core.database.dao.BreedDao
import com.tcastro.data.core.database.dao.RemoteKeyDao
import com.tcastro.data.core.database.entity.BreedEntity
import com.tcastro.data.core.database.entity.RemoteKeyEntity


private const val STARTING_PAGE = 0
private const val PAGE_SIZE = 20

@OptIn(ExperimentalPagingApi::class)
class BreedsMediator(
    private val apiService: BreedsService,
    private val database: SwordCatsDatabase,
    private val breedDao: BreedDao,
    private val remoteKeyDao: RemoteKeyDao
) : RemoteMediator<Int, BreedEntity>() {

    override suspend fun initialize(): InitializeAction {
        val hasData = breedDao.getCount() > 0
        return if (hasData) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> STARTING_PAGE

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val remoteKey = remoteKeyDao.getRemoteKey(lastItem.id)
                    remoteKey?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = apiService.getBreeds(
                limit = PAGE_SIZE,
                page = page
            )

            val endOfPaginationReached = response.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    breedDao.clearAll()
                    remoteKeyDao.clearAll()
                }

                val nextPage = if (endOfPaginationReached) null else page + 1
                val prevPage = if (page == STARTING_PAGE) null else page - 1

                val remoteKeys = response.map { dto ->
                    RemoteKeyEntity(
                        breedId = dto.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                breedDao.insertAll(response.map { it.toEntity() })
                remoteKeyDao.insertAll(remoteKeys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}