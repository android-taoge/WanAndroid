package com.example.jetpackpaging.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackpaging.model.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ArticleEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(articleEntity: ArticleEntity)

    @Query("select * from article order by insertIndex ASC ")
    fun queryArticle(): PagingSource<Int, ArticleEntity>

    @Query("delete from article")
    suspend fun clear()

}