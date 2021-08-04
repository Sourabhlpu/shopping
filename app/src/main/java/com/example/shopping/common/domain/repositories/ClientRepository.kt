package com.example.shopping.common.domain.repositories

import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.client.details.ClientWithTodos
import com.example.shopping.common.domain.model.pagination.PaginatedClients
import com.example.shopping.common.domain.model.pagination.PaginatedTodos
import com.example.shopping.common.domain.model.todos.Todo
import io.reactivex.Flowable

interface ClientRepository {
    fun getClients(): Flowable<List<Client>>
    suspend fun requestMoreClients(pageToLoad: Int, pageSize: Int): PaginatedClients
    suspend fun storeClients(clients: List<Client>)
    suspend fun storeTodos(todos: List<Todo>, clientId: Long)
    suspend fun getTodosForClient(clientId: Long, pageToLoad: Int, pageSize: Int): PaginatedTodos
    fun getClientWithTodo(clientId: Long): Flowable<List<ClientWithTodos>>
}