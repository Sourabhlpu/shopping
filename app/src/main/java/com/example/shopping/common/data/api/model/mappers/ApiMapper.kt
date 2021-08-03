package com.example.shopping.common.data.api.model.mappers

import android.provider.ContactsContract

interface ApiMapper<E, D> {
    fun mapToDomain(apiEntity: E) : D
}