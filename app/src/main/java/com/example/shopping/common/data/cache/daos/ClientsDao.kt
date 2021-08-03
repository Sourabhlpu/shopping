package com.example.shopping.common.data.cache.daos

import androidx.room.*
import com.example.shopping.common.data.cache.models.cachedclient.CachedClient
import com.example.shopping.common.data.cache.models.cachedclient.CachedClientAggregate
import io.reactivex.Flowable

@Dao
abstract class ClientsDao {
    @Transaction
    @Query("SELECT * FROM clients")
    abstract fun getAllClients() : Flowable<List<CachedClientAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertClients(clients: List<CachedClient>)
}