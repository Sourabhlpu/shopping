package com.example.shopping.common.data.cache

import com.example.shopping.common.data.cache.models.cachedclient.CachedClient
import com.example.shopping.common.data.cache.models.cachedclient.CachedClientAggregate
import com.example.shopping.common.data.cache.models.cachedtodo.CachedTodo
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow

interface Cache {
    suspend fun storeTodos(todos: List<CachedTodo>)
    fun getClients(): Flowable<List<CachedClientAggregate>>
    suspend fun storeClients(clients: List<CachedClient>)
}