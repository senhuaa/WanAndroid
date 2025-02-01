package com.seirar.wanandroid.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seirar.wanandroid.data.repository.ArticleRepository
import com.seirar.wanandroid.domain.model.article.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles = _articles.asStateFlow()

    private var currentPage = 0
    private var isEndReached = false

    init {
        initialData()
    }

    private fun initialData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getCachedArticles().collect{ cached ->
                    _articles.value = cached
                }

                val freshData = repository.fetchArticle(0)
                currentPage = 0
                isEndReached = false
                _articles.value = freshData
            } catch (e: Exception) {
                e.message?.let { Log.d("ViewModel", it) }
            }
        }
    }

    fun loadMoreData() {
        if (isEndReached) return

        viewModelScope.launch {
            try {

                val newData = repository.fetchArticle(currentPage + 1)
                currentPage++
                isEndReached = newData.isEmpty()

                _articles.value += newData
            } catch (e: Exception) {
                e.message?.let { Log.d("ViewModel", it) }
            }
        }
    }

    fun refreshData() {
        initialData()
    }

}