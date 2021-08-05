package com.example.shopping.common.data.cache

import com.example.shopping.common.data.cache.daos.ClientsDao
import com.example.shopping.common.data.cache.daos.TodosDao
import com.example.shopping.common.data.cache.models.cachedclient.CachedClient
import com.example.shopping.common.data.cache.models.cachedclient.CachedClientAggregate
import com.example.shopping.common.data.cache.models.cachedtodo.CachedTodo
import io.reactivex.Flowable
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val clientsDao: ClientsDao,
    private val todosDao: TodosDao
) : Cache {
    override suspend fun storeTodos(todos: List<CachedTodo>) {
        todosDao.insertTodos(todos)
    }

    override fun getClients(): Flowable<List<CachedClient>> {
        return clientsDao.getAllClients()
    }

    override fun getClient(clientId: Long): Flowable<CachedClientAggregate> {
        return clientsDao.getClient(clientId)
    }

    override suspend fun storeClients(clients: List<CachedClient>) {
        clientsDao.insertClients(clients)
    }
}