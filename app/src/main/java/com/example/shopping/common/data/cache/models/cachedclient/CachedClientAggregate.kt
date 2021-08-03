package com.example.shopping.common.data.cache.models.cachedclient

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shopping.common.data.cache.models.cachedtodo.CachedTodo

data class CachedClientAggregate(
    @Embedded
    val client: CachedClient,
    @Relation(
        parentColumn = "clientId",
        entityColumn = "clientId"
    )
    val todos: List<CachedTodo>
)