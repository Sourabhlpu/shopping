package com.example.shopping.common.data

import com.example.shopping.common.data.api.ClientsApi
import com.example.shopping.common.data.api.model.mappers.ApiClientMapper
import com.example.shopping.common.data.api.model.mappers.ApiPaginationMapper
import com.example.shopping.common.data.cache.Cache
import com.example.shopping.common.data.cache.models.cachedclient.CachedClient
import com.example.shopping.common.domain.NetworkException
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.pagination.PaginatedClients
import com.example.shopping.common.domain.repositories.ClientRepository
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

class ShoppingAppClientsRepository @Inject constructor(
    private val api: ClientsApi,
    private val cache: Cache,
    private val apiClientMapper: ApiClientMapper,
    private val apiPaginationMapper: ApiPaginationMapper
) : ClientRepository {
    override  fun getClients(): Flowable<List<Client>> {
        return cache.getClients()
            .distinctUntilChanged()
            .map { clientList ->
                clientList.map { it.client.toDomain() }
            }
    }

    override suspend fun requestMoreClients(pageToLoad: Int, numberOfItems: Int): PaginatedClients {
        try {
            val (meta, apiClients) = api.getClients(
                pageToLoad,
                numberOfItems
            )

            return PaginatedClients(
                apiClients?.map { apiClientMapper.mapToDomain(it) }.orEmpty(),
                apiPaginationMapper.mapToDomain(meta?.pagination)
            )
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeClients(clients: List<Client>) {
        cache.storeClients(clients.map { CachedClient.fromDomain(it) })
    }

}