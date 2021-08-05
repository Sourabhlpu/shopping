package com.example.shopping.common.data.cache.models.cachedclient

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shopping.common.data.cache.models.cachedtodo.CachedTodo
import com.example.shopping.common.domain.model.client.Client
import com.example.shopping.common.domain.model.client.details.ClientWithTodos

        data class CachedClientAggregate(
            @Embedded
            val client: CachedClient,
            @Relation(
                parentColumn = "clientId",
                entityColumn = "clientId"
            )
            val todos: List<CachedTodo>
        ){
    fun toDomain() : ClientWithTodos{
        val(id, name, email, gender, status) = client.toDomain()
        return ClientWithTodos(
            id,
            name,
            email,
            gender,
            status,
            todos.map { it.toDomain() }
        )
    }
}