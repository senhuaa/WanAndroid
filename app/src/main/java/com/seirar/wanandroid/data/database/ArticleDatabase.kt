package com.seirar.wanandroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seirar.wanandroid.domain.model.article.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDatabase :RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}