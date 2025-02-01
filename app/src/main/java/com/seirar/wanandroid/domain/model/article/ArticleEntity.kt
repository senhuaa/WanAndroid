package com.seirar.wanandroid.domain.model.article

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val author: String,
    val url: String,
    val date: String,
    val category: String,
    val isCollected: Boolean
)

// 转换方法扩展
fun Article.toEntity() = ArticleEntity(
    id = id,
    title = title,
    author = author,
    url = url,
    date = date,
    category = category,
    isCollected = isCollected
)

fun ArticleEntity.toDomainModel() = Article(
    id = id,
    title = title,
    author = author,
    url = url,
    date = date,
    category = category,
    isCollected = isCollected
)