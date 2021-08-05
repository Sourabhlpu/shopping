package com.example.shopping.common.data.cache.models.cachedclient

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.client.details.Gender
import com.example.shopping.common.domain.model.client.details.Status

@Entity(tableName = "clients")
    data class CachedClient(
        @PrimaryKey(autoGenerate = false)
        val clientId: Long,
        val name: String,
        val email: String,
        val gender: String,
        val status: String
    ) {
    companion object {
        fun fromDomain(domainModel: Client): CachedClient {
            return CachedClient(
                clientId = domainModel.id,
                name = domainModel.name,
                email = domainModel.email,
                gender = domainModel.gender.toString(),
                status = domainModel.status.toString()
            )
        }
    }

    fun toDomain(): Client {
        return Client(
            clientId,
            name,
            email,
            Gender.valueOf(gender),
            Status.valueOf(status)
        )

    }
}