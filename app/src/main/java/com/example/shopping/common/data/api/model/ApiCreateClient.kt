package com.example.shopping.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCreateClient(
    @field: Json(name = "meta") val meta: Meta?,
    @field: Json(name = "data") val data: ApiClient?)