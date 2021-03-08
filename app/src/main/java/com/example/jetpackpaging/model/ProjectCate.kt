package com.example.jetpackpaging.model

import java.io.Serializable

data class ProjectCate(
    val children: List<Any> = listOf(),
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
):Serializable