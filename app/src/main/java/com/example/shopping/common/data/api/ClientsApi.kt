package com.example.shopping.common.data.api

import com.example.shopping.common.data.api.model.ApiPaginatedClients
import com.example.shopping.common.data.api.model.ApiPaginatedTodos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientsApi {
    @GET(ApiConstants.CLIENTS_ENDPOINT)
    suspend fun getClients(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int
    ): ApiPaginatedClients

    @GET(ApiConstants.CLIENT_TODO_ENDPOINT)
    suspend fun getClientTodos(
        @Path("id") id: Long,
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int

    ): ApiPaginatedTodos
}