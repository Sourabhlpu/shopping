package com.example.shopping.common.presentation.model.clientdetails

data class UIClientWithTodos(
    val id: Long,
    val name: String,
    val gender: String,
    val todos: List<UITodo>
)