package com.example.jetpackpaging.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "article")
data class ArticleEntity(
    @field:SerializedName("apkLink") var apkLink: String = "",
    @Ignore var audit: Int = 0,
    @field:SerializedName("author") var author: String = "",
    @Ignore var canEdit: Boolean = false,
    @Ignore var chapterId: Int = 0,
    @Ignore var chapterName: String = "",
    @Ignore var collect: Boolean = false,
    @Ignore var courseId: Int = 0,
    @field:SerializedName("desc") var desc: String = "",
    @Ignore var descMd: String = "",
    @Ignore var envelopePic: String = "",
    @Ignore var fresh: Boolean = false,
    @Ignore var host: String = "",
    @field:SerializedName("id") var id: Int = 0,
    @field:SerializedName("link") var link: String = "",
    @Ignore var niceDate: String = "",
    @Ignore var niceShareDate: String = "",
    @Ignore var origin: String = "",
    @Ignore var prefix: String = "",
    @Ignore var projectLink: String = "",
    @field:SerializedName("publishTime") var publishTime: Long = 0,
    @Ignore var realSuperChapterId: Int = 0,
    @Ignore var selfVisible: Int = 0,
    @Ignore var shareDate: Long = 0,
    @Ignore var shareUser: String = "",
    @Ignore var superChapterId: Int = 0,
    @Ignore var superChapterName: String = "",
    @Ignore var tags: List<Tag> = listOf(),
    @field:SerializedName("title") var title: String = "",
    @field:SerializedName("type") var type: Int = 0,
    @field:SerializedName("userId") var userId: Int = 0,
    @Ignore var visible: Int = 0,
    @Ignore var zan: Int = 0,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("insertIndex") var insertIndex:Int=0

    )


data class Tag(
    val name: String = "公众号",
    val url: String = "/wxarticle/list/413/1"
)