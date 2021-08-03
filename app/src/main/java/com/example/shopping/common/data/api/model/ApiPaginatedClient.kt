package com.example.shopping.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginatedClients(
    @field: Json(name = "meta") val meta: Meta?,
    @field: Json(name = "data") val data: List<ApiClient>?
)

@JsonClass(generateAdapter = true)
data class Meta(
    @field: Json(name = "pagination") val pagination: ApiPagination
)

@JsonClass(generateAdapter = true)
data class ApiPagination(
    @field:Json(name = "limit") val countPerPage: Int?,
    @field:Json(name = "total") val totalCount: Int?,
    @field:Json(name = "page") val currentPage: Int?,
    @field:Json(name = "pages") val totalPages: Int?
)