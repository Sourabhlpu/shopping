package com.example.shopping.common.domain.model.pagination

import com.example.shopping.common.domain.model.client.Client

data class PaginatedClients(
    val clients: List<Client>,
    val pagination: Pagination
)