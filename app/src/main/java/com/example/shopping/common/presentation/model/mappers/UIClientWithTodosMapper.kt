package com.example.shopping.common.presentation.model.mappers

import com.example.shopping.common.domain.model.client.details.ClientWithTodos
import com.example.shopping.common.presentation.model.clientdetails.UIClientWithTodos
import javax.inject.Inject

class UIClientWithTodosMapper @Inject constructor(private val todosMapper: UITodosMapper) : UiMapper<ClientWithTodos, UIClientWithTodos> {
    override fun mapToView(input: ClientWithTodos): UIClientWithTodos {
        return UIClientWithTodos(
            id = input.id,
            name = input.name,
            email = input.email,
            gender = input.gender.toString(),
            status = input.status.toString(),
            todos = input.todos.map { todosMapper.mapToView(it) }
        )
    }
}