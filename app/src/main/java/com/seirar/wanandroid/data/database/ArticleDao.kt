package com.seirar.wanandroid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.seirar.wanandroid.domain.model.article.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll(): Flow<List<ArticleEntity>>

    @Insert
    fun insertAll(article: List<ArticleEntity>)

}