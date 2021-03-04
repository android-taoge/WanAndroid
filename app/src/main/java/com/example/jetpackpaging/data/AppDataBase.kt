package com.example.jetpackpaging.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackpaging.data.dao.ArticleDao
import com.example.jetpackpaging.data.dao.RemoteKeysDao
import com.example.jetpackpaging.model.ArticleEntity
import com.example.jetpackpaging.model.RemoteKeys


@Database(
    entities = [ArticleEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {


    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao


    companion object {

        private var INSTANCE: AppDataBase? = null


        fun getInstance(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createDataBase(context).also {
                    INSTANCE = it
                }
            }

        private fun createDataBase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, "app_db").build()
        }
    }
}