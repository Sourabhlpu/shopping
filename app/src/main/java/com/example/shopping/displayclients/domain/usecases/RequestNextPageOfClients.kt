package com.example.shopping.displayclients.domain.usecases

import com.example.shopping.common.domain.NoMoreClientsException
import com.example.shopping.common.domain.model.pagination.Pagination
import com.example.shopping.common.domain.repositories.ClientRepository
import javax.inject.Inject

class RequestNextPageOfClients @Inject constructor(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ) : Pagination{
        val (clients, pagination) = clientRepository.requestMoreClients(pageToLoad, pageSize)

        if(clients.isEmpty()){
            throw NoMoreClientsException("No clients")
        }

        clientRepository.storeClients(clients)
        return pagination
    }
}