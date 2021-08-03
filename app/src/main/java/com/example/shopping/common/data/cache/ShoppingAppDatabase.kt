package com.example.shopping.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopping.common.data.cache.daos.ClientsDao
import com.example.shopping.common.data.cache.daos.TodosDao
import com.example.shopping.common.data.cache.models.cachedclient.CachedClient
import com.example.shopping.common.data.cache.models.cachedtodo.CachedTodo

@Database(
    entities = [
        CachedClient::class,
        CachedTodo::class
    ],
    version = 1
)
abstract class ShoppingAppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientsDao
    abstract fun todoDao(): TodosDao
}