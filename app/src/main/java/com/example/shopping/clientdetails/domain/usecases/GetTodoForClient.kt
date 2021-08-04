package com.example.shopping.clientdetails.domain.usecases

import com.example.shopping.common.domain.NoMoreTodosException
import com.example.shopping.common.domain.model.pagination.Pagination
import com.example.shopping.common.domain.repositories.ClientRepository
import javax.inject.Inject

class GetTodoForClient @Inject constructor(
    private val clientsRepository: ClientRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        clientId: Long, pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {

        val (todos, pagination) = clientsRepository.getTodosForClient(
            clientId,
            pageToLoad,
            pageSize
        )

        if (todos.isEmpty()) {
            throw NoMoreTodosException("No Todos")
        }
        clientsRepository.storeTodos(todos, clientId)
        return pagination
    }
}