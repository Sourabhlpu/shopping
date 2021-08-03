package com.example.shopping.common.data.di

import android.content.Context
import androidx.room.Room
import com.example.shopping.common.data.cache.Cache
import com.example.shopping.common.data.cache.RoomCache
import com.example.shopping.common.data.cache.ShoppingAppDatabase
import com.example.shopping.common.data.cache.daos.ClientsDao
import com.example.shopping.common.data.cache.daos.TodosDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): ShoppingAppDatabase {
            return Room.databaseBuilder(
                context,
                ShoppingAppDatabase::class.java,
                "shoppingApp.db"
            )
                .build()
        }

        @Provides
        fun provideClientsDao(
            shoppingAppDatabase: ShoppingAppDatabase
        ): ClientsDao = shoppingAppDatabase.clientDao()

        @Provides
        fun provideTodoDao(shoppingAppDatabase: ShoppingAppDatabase): TodosDao =
            shoppingAppDatabase.todoDao()
    }
}