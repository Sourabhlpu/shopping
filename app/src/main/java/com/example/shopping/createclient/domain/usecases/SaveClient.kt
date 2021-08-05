package com.example.shopping.createclient.domain.usecases

import com.example.shopping.common.domain.CreateUserException
import com.example.shopping.common.domain.NoMoreClientsException
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.repositories.ClientRepository
import com.example.shopping.common.presentation.model.clientdetails.UIClientWithDetails
import javax.inject.Inject

class SaveClient @Inject constructor(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(client: Client) : Boolean  {
      val client = clientRepository.createClient(client) ?: throw CreateUserException("Failed to save user")
        clientRepository.storeClients(listOf(client))
        return true
    }
}