package com.example.shopping.common.data.api.model.mappers

import com.example.shopping.common.data.api.model.ApiClient
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.client.details.Gender
import com.example.shopping.common.domain.model.client.details.Status
import java.util.*
import javax.inject.Inject

class ApiClientMapper @Inject constructor() : ApiMapper<ApiClient, Client> {
    override fun mapToDomain(apiEntity: ApiClient): Client {
        return Client(
            id = apiEntity.id ?: throw MappingException("Client Id cannot be null"),
            name = apiEntity.name.orEmpty(),
            email = apiEntity.email.orEmpty(),
            gender = parseGender(apiEntity.gender),
            status = parseStatus(apiEntity.status)
        )
    }

    private fun parseStatus(status: String?): Status {
        if(status.isNullOrEmpty()) return Status.ALL

        return Status.valueOf(status.toUpperCase(Locale.ROOT))
    }

    private fun parseGender(gender: String?): Gender {
        if (gender.isNullOrEmpty()) return Gender.UNKNOWN

        return Gender.valueOf(gender.toUpperCase(Locale.ROOT))
    }

}