package com.seirar.wanandroid.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seirar.wanandroid.data.repository.ArticleRepository
import com.seirar.wanandroid.domain.model.article.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentPage = 0

    init {
        initialData()
    }

    private fun initialData() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO){
                    repository.getCachedArticles()
                        .take(1)
                        .collect { cached ->
                            if (cached.isNotEmpty()){
                                _uiState.value = HomeUiState.Success(
                                    articles = cached,
                                    isRefreshing = true
                                )
                            }
                        }
                    repository.clearOldCache()
                }

                val freshData = withContext(Dispatchers.IO) {
                    repository.fetchArticle(0)
                }

                currentPage = 0
                _uiState.value = HomeUiState.Success(articles = freshData)

                withContext(Dispatchers.Main){
                    _uiEvent.send(HomeUiEvent.ScrollToTop)
                }
            } catch (e: Exception) {
                val currentState = _uiState.value
                if (currentState is HomeUiState.Success) {
                    _uiState.value = currentState.copy(isRefreshing = false)

                    withContext(Dispatchers.Main) {
                        _uiEvent.send(
                            HomeUiEvent.ShowSnackbar(
                                message = e.message ?: "Unknown error occurred"
                            )
                        )
                    }
                }
            }
        }
    }

    fun loadMoreData() {
        val currentState = _uiState.value as? HomeUiState.Success ?: return
        if (currentState.isPaginateLoading || currentState.hasReachedEnd) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = currentState.copy(isPaginateLoading = true)

                val newData = repository.fetchArticle(currentPage + 1)
                if (newData.isNotEmpty()) {
                    currentPage++
                    _uiState.value = currentState.copy(
                        articles = currentState.articles + newData,
                        isPaginateLoading = false
                    )
                } else {
                    _uiState.value = currentState.copy(
                        isPaginateLoading = false,
                        hasReachedEnd = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isPaginateLoading = false,
                )

                withContext(Dispatchers.Main) {
                    _uiEvent.send(
                        HomeUiEvent.ShowSnackbar(
                            message = e.message ?: "Failed to load"
                        )
                    )
                }
            }
        }
    }

    fun refreshData() {
        val currentState = _uiState.value as? HomeUiState.Success ?: return

        viewModelScope.launch {
            try {
                _uiState.value = currentState.copy(isRefreshing = true)
                currentPage = 0

                val freshData = repository.fetchArticle(0)
                _uiState.value = HomeUiState.Success(
                    articles = freshData,
                    isRefreshing = false,
                    hasReachedEnd = false
                )

                withContext(Dispatchers.Main){
                    _uiEvent.send(HomeUiEvent.ScrollToTop)
                }
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isRefreshing = false,
                )

                withContext(Dispatchers.Main){
                    _uiEvent.send(
                        HomeUiEvent.ShowSnackbar(
                            message = e.message ?: "Failed to refresh"
                        )
                    )
                }
            }
        }
    }
}

sealed interface HomeUiState{
    data object Loading: HomeUiState
    data class Success(
        val articles: List<Article>,
        val isRefreshing: Boolean = false,
        val isPaginateLoading: Boolean = false,
        val hasReachedEnd: Boolean = false,
    ): HomeUiState
}

sealed interface HomeUiEvent {
    data object ScrollToTop : HomeUiEvent
    data class ShowSnackbar(val message: String) : HomeUiEvent
}