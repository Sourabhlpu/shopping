package com.example.shopping.common.domain.model.pagination

import com.example.shopping.common.domain.model.todos.Todo

data class PaginatedTodos(
    val todos: List<Todo>,
    val pagination: Pagination
)