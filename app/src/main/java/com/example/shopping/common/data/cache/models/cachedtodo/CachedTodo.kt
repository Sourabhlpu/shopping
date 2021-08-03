package com.example.shopping.common.data.cache.models.cachedtodo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.shopping.common.data.cache.models.cachedclient.CachedClient
import com.example.shopping.common.domain.model.todos.Todo
import com.example.shopping.common.domain.model.todos.TodoStatus
import com.example.shopping.common.utils.DateTimeUtils


@Entity(
    tableName = "todo",
    foreignKeys = [
        ForeignKey(
            entity = CachedClient::class,
            parentColumns = ["clientId"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("clientId")]
)
data class CachedTodo(
    @PrimaryKey(autoGenerate = false)
    val todoId: Long,
    val clientId: Long,
    val title: String,
    val dueOn: String,
    val status: String
) {
    companion object {
        fun fromDomain(clientId: Long, domainModel: Todo): CachedTodo {
            return CachedTodo(
                todoId = domainModel.id,
                clientId = clientId,
                title = domainModel.title,
                dueOn = domainModel.dueOn.toString(),
                status = domainModel.status.toString()
            )
        }
    }

    fun toDomain() : Todo {
        return Todo(
            todoId,
            title,
            DateTimeUtils.parse(dueOn),
            TodoStatus.valueOf(status)
        )
    }
}