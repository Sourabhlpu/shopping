package com.example.shopping.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiTodo(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "user_id") val userId: Long?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "due_on") val dueOn: String?,
    @field:Json(name = "status") val status: String?,
) {
}