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

    @Transaction
    @Query("SELECT * FROM clients WHERE clientId = :clientId")
    abstract fun getClient(clientId: Long) : Flowable<CachedClientAggregate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertClients(clients: List<CachedClient>)
}