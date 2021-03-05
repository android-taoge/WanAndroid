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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKeys: RemoteKeys)


    @Query("select * from remoteKey where articleId=:id")
    suspend fun queryRemoteKey(id:Int): RemoteKeys


    @Query("delete from remoteKey")
    suspend fun clear()

}