package com.example.shopping.common.data.api

import com.example.shopping.common.data.api.model.ApiPaginatedClients
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientsApi {
    @GET(ApiConstants.CLIENTS_ENDPOINT)
    suspend fun getClients(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int
    ) : ApiPaginatedClients
}