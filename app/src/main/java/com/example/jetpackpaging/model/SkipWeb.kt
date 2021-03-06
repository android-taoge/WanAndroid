package com.example.jetpackpaging.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SkipWeb(
    @field:SerializedName("id") val id: Int = 0,
    @field:SerializedName("link") val link: String = "",
    @field:SerializedName("title") val title: String = ""
) : Serializable