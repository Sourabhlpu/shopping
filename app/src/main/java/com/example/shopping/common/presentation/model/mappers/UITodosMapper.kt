package com.example.shopping.common.presentation.model.mappers

import com.example.shopping.common.domain.model.todos.Todo
import com.example.shopping.common.presentation.model.clientdetails.UITodo
import javax.inject.Inject

class UITodosMapper @Inject constructor(): UiMapper<Todo, UITodo> {
    override fun mapToView(input: Todo): UITodo {
        return UITodo(
            id = input.id,
            title = input.title,
            dueOn = input.dueOn.toString(),
            status = input.status.toString()
        )
    }

}