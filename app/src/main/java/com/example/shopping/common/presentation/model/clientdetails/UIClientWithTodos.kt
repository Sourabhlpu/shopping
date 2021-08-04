package com.example.shopping.common.presentation.model.clientdetails

import android.provider.ContactsContract

data class UIClientWithTodos(
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,
    val todos: List<UITodo>
) {
    constructor() : this(
        -1,
        "",
        "",
        "",
        "",
        emptyList()

    )

    val isEmpty
    get() = id == -1L &&
            name.isEmpty() &&
            email.isEmpty() &&
            gender.isEmpty()
            && status.isEmpty()
            && todos.isEmpty()

}