package com.example.shopping.displayclients.domain.usecases

import com.example.shopping.common.domain.repositories.ClientRepository
import javax.inject.Inject

class GetClients @Inject constructor(
    private val clientsRepository: ClientRepository
){
    operator fun invoke() = clientsRepository.getClients().filter{it.isNotEmpty()}
}