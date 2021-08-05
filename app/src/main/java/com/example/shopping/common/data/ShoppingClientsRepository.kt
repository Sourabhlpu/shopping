package com.example.shopping.common.data

import com.example.shopping.common.data.api.ClientsApi
import com.example.shopping.common.data.api.model.mappers.ApiClientMapper
import com.example.shopping.common.data.api.model.mappers.ApiPaginationMapper
import com.example.shopping.common.data.api.model.mappers.ApiTodosMapper
import com.example.shopping.common.data.cache.Cache
import com.example.shopping.common.data.cache.models.cachedclient.CachedClient
import com.example.shopping.common.data.cache.models.cachedtodo.CachedTodo
import com.example.shopping.common.domain.NetworkException
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.client.details.ClientWithTodos
import com.example.shopping.common.domain.model.pagination.PaginatedClients
import com.example.shopping.common.domain.model.pagination.PaginatedTodos
import com.example.shopping.common.domain.model.todos.Todo
import com.example.shopping.common.domain.repositories.ClientRepository
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

class ShoppingAppClientsRepository @Inject constructor(
    private val api: ClientsApi,
    private val cache: Cache,
    private val apiClientMapper: ApiClientMapper,
    private val apiTodosMapper: ApiTodosMapper,
    private val apiPaginationMapper: ApiPaginationMapper
) : ClientRepository {
    override fun getClients(): Flowable<List<Client>> {
        return cache.getClients()
            .distinctUntilChanged()
            .map { clientList ->
                clientList.map { it.toDomain() }
            }
    }

    override fun getClientWithTodo(clientId: Long): Flowable<ClientWithTodos> {
        return cache.getClient(clientId)
            .distinctUntilChanged()
            .map { it.toDomain() }
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

    override suspend fun storeTodos(todos: List<Todo>, clientId: Long) {
        cache.storeTodos(todos.map { CachedTodo.fromDomain(clientId, it) })
    }

    override suspend fun getTodosForClient(
        clientId: Long,
        pageToLoad: Int,
        numberOfItems: Int
    ): PaginatedTodos {
        try {
            val (apiPagination, apiTodos) = api.getClientTodos(
                clientId,
                pageToLoad,
                numberOfItems
            )

            return PaginatedTodos(
                apiTodos?.map { apiTodosMapper.mapToDomain(it) }.orEmpty(),
                apiPaginationMapper.mapToDomain(apiPagination?.pagination)
            )
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }
}