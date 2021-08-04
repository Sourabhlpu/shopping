package com.example.shopping.clientdetails.domain.usecases

import com.example.shopping.common.domain.repositories.ClientRepository
import javax.inject.Inject

class GetClientWithTodos @Inject constructor(
    private val clientsRepository: ClientRepository
    ) {
    operator fun invoke(clientId: Long) = clientsRepository.getClientWithTodo(clientId)
}