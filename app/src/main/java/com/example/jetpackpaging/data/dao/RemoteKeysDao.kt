package com.example.jetpackpaging.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackpaging.model.RemoteKeys


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<RemoteKeys>)

    @Query("select * from remoteKey where articleId =:articleId  ")
    suspend fun queryRemoteKey(articleId: Int): RemoteKeys?

    @Query("delete from remoteKey")
    suspend fun clearRemoteKeys()
}