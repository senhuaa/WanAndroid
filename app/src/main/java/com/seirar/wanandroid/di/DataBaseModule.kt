package com.seirar.wanandroid.di

import android.content.Context
import androidx.room.Room
import com.seirar.wanandroid.data.database.ArticleDao
import com.seirar.wanandroid.data.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context) : ArticleDatabase {
        return Room.databaseBuilder(
            context = context,
            ArticleDatabase::class.java,
            "article_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: ArticleDatabase) : ArticleDao {
        return database.articleDao()
    }

}