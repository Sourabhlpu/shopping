package com.example.shopping.common.domain.model.todos

import com.example.shopping.common.domain.model.client.details.Status
import org.threeten.bp.LocalDateTime


data class Todo(
    val id: Long,
    val title: String,
    val dueOn: LocalDateTime,
    val status: TodoStatus
) {
    constructor() : this(-1, "", LocalDateTime.now(), TodoStatus.PENDING)
}