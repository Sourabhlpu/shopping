package com.example.shopping.common.presentation.model.mappers

import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.client.details.Gender
import com.example.shopping.common.domain.model.client.details.Status
import com.example.shopping.common.presentation.model.clientdetails.UIClientWithDetails
import java.util.*
import javax.inject.Inject

class UiClientDetailsMapper @Inject constructor() : UiMapper<Client, UIClientWithDetails> {

    override fun mapToView(input: Client): UIClientWithDetails {
        return UIClientWithDetails(
            id = input.id,
            name = input.name,
            email = input.email,
            gender = input.gender.toString(),
            status = input.status.toString()

        )
    }

    override fun mapToDomain(input: UIClientWithDetails): Client {
        return Client(
            id = input.id,
            name = input.name,
            email = input.email,
            gender = parseGender(input.gender),
            status = parseStatus(input.status)

        )
    }

    private fun parseStatus(status: String?): Status {
        if(status.isNullOrEmpty()) return Status.ALL
        return Status.valueOf(status.toUpperCase(Locale.ROOT))
    }

    private fun parseGender(gender: String?): Gender {
        if(gender.isNullOrEmpty()) return Gender.UNKNOWN

        return Gender.valueOf(gender.toUpperCase(Locale.ROOT))
    }
}