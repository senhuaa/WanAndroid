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
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles = _articles.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private var currentPage = 0
    private var isEndReached = false
    private var isPaginateLoading = false

    init {
        initialData()
    }

    private fun initialData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getCachedArticles()
                    .take(1)
                    .collect { cached ->
                        if (cached.isNotEmpty()) {
                            _articles.value = cached
                        }
                    }

                repository.clearOldCache()

                _isRefreshing.value = true
                val freshData = repository.fetchArticle(0)

                currentPage = 0
                _articles.value = freshData
            } catch (e: Exception) {
                e.message?.let { Log.d("ViewModel", it) }
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun loadMoreData() {
        if (isEndReached || isPaginateLoading) return

        isPaginateLoading = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newData = repository.fetchArticle(currentPage + 1)
                if (newData.isNotEmpty()) {
                    currentPage++
                    _articles.value += newData
                    Log.d("ViewModel", currentPage.toString())
                } else {
                    isEndReached = true
                }
            } catch (e: Exception) {
                e.message?.let { Log.d("ViewModel", "loadMoreData: $it") }
            } finally {
                isPaginateLoading = false
            }
        }
    }

    fun refreshData() {
        initialData()
    }

}