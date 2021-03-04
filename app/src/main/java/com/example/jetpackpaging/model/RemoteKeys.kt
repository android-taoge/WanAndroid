package com.example.jetpackpaging.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "remoteKey")
data class RemoteKeys(
    @PrimaryKey @field:SerializedName("articleId") val articleId: Int,
    @field:SerializedName("prevKey") val prevKey: Int?,
    @field:SerializedName("nextKey") val nextKey: Int?
)