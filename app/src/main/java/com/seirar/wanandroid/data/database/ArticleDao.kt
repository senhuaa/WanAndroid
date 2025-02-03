package com.seirar.wanandroid.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.seirar.wanandroid.domain.model.article.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM articles")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM articles")
    suspend fun getArticleCount(): Int

    @Query("DELETE FROM articles WHERE date < :threshold")
    suspend fun deleteOldArticles(threshold: String)

    @Transaction
    suspend fun updateArticlesTransaction(article: List<ArticleEntity>) {
        clearAll()
        insertAll(article)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(article: List<ArticleEntity>)

}