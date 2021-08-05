package com.example.shopping.common.domain.model.client.details

import com.example.shopping.common.domain.model.todos.Todo


data class ClientWithTodos(
    val id: Long,
    val name: String,
    val email: String,
    val gender: Gender,
    val status: Status,
    val todos: List<Todo>
){
    constructor() : this(
        -1,
        "",
        "",
        Gender.UNKNOWN,
        Status.ALL,
        emptyList()
    )
}