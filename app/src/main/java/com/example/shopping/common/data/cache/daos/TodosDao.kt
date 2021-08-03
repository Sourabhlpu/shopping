package com.example.shopping.common.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.shopping.common.data.cache.models.cachedtodo.CachedTodo

@Dao
abstract class TodosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTodos(clients: List<CachedTodo>)
}