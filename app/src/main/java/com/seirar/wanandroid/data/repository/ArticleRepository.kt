package com.seirar.wanandroid.data.repository

import com.seirar.wanandroid.data.database.ArticleDao
import com.seirar.wanandroid.domain.model.article.Article
import com.seirar.wanandroid.domain.model.article.ArticleApiService
import com.seirar.wanandroid.domain.model.article.toDomainModel
import com.seirar.wanandroid.domain.model.article.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(
    private val apiService: ArticleApiService,
    private val articleDao: ArticleDao
) {
    suspend fun fetchArticle(page: Int): List<Article>{
        val response = apiService.getArticles(page = page)
        if (response.errorCode == 0) {
            val article = response.data.datas.map { it.toDomainModel() }

            if (page == 0) {
                articleDao.updateArticlesTransaction(article.map { it.toEntity() })
            }

            return article
        }
        throw ApiException(response.errorCode, response.errorMsg)
    }

    suspend fun clearOldCache() {
        val oneWeekAgo = LocalDateTime.now().minusWeeks(1).toString()
        articleDao.deleteOldArticles(oneWeekAgo)
    }

    fun getCachedArticles(): Flow<List<Article>> {
        return articleDao.getAll().map { entity->
            entity.map { it.toDomainModel() }
        }
    }
}

class ApiException(
    val code: Int,
    override val message: String
) : IOException(message)