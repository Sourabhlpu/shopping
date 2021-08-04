package com.example.shopping.common.presentation.model.mappers

import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.presentation.model.clients.UIClient
import javax.inject.Inject

class UiClientMapper @Inject constructor(): UiMapper<Client, UIClient> {

    override fun mapToView(input: Client): UIClient {
        return UIClient(
            id = input.id,
            name = input.name,
            gender = input.gender.toString()
        )
    }
}