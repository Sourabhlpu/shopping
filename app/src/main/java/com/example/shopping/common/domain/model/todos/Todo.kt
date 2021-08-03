package com.example.shopping.common.domain.model.todos

import org.threeten.bp.LocalDateTime


data class Todo(
    val id: Long,
    val title: String,
    val dueOn: LocalDateTime,
    val status: TodoStatus
)