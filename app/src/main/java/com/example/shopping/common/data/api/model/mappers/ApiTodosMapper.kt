package com.example.shopping.common.data.api.model.mappers

import com.example.shopping.common.data.api.model.ApiClient
import com.example.shopping.common.data.api.model.ApiTodo
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.client.details.Gender
import com.example.shopping.common.domain.model.client.details.Status
import com.example.shopping.common.domain.model.todos.Todo
import com.example.shopping.common.domain.model.todos.TodoStatus
import com.example.shopping.common.utils.DateTimeUtils
import java.util.*
import javax.inject.Inject

class ApiTodosMapper @Inject constructor() : ApiMapper<ApiTodo, Todo> {
    override fun mapToDomain(apiEntity: ApiTodo): Todo {
        return Todo(
            id = apiEntity.id ?: throw MappingException("todo Id cannot be null"),
            title = apiEntity.title.orEmpty(),
            dueOn = DateTimeUtils.parse(apiEntity.dueOn.orEmpty()),
            status = parseStatus(apiEntity.status)
        )
    }

    private fun parseStatus(status: String?): TodoStatus {
        if(status.isNullOrEmpty()) return TodoStatus.PENDING

        return TodoStatus.valueOf(status.toUpperCase(Locale.ROOT))
    }
}