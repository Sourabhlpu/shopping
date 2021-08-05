package com.example.shopping.common.data.api

import com.example.shopping.common.data.api.model.ApiClient
import com.example.shopping.common.data.api.model.ApiPaginatedClients
import com.example.shopping.common.data.api.model.ApiPaginatedTodos
import com.example.shopping.common.data.api.model.ApiCreateClient
import retrofit2.http.*

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


    @POST(ApiConstants.CLIENTS_ENDPOINT)
    suspend fun saveClient(
        @Header(ApiParameters.AUTH_HEADER) token : String,
        @Body client: ApiClient
    ) : ApiCreateClient
}