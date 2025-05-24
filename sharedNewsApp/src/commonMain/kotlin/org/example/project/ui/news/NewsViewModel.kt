package org.example.project.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.repository.GetNewsRepository
import org.example.project.model.ApiResult
import org.example.project.ui.news.NewsUiState

class NewsViewModel(private val getNewsRepository: GetNewsRepository = GetNewsRepository()) :
    ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val pageSize = 5

    init {
        loadNews()
    }

    fun loadNews(refresh: Boolean = false) {
        viewModelScope.launch {

            val page = if (refresh) 1 else _uiState.value.currentPage + 1

            if (refresh) {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    error = null,
                    currentPage = 1
                )
            } else if (_uiState.value.isLoading || _uiState.value.isLoadingMore || !_uiState.value.hasMore) {
                return@launch
            } else if (page == 1) {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            } else {
                _uiState.value = _uiState.value.copy(isLoadingMore = true, error = null)
            }

            when (val result = getNewsRepository.getNews(page, pageSize)) {
                is ApiResult.Success -> {
                    val currentNews = if (refresh) emptyList() else _uiState.value.news
                    _uiState.value = _uiState.value.copy(
                        news = currentNews + result.data,
                        isLoading = false,
                        isLoadingMore = false,
                        error = null,
                        hasMore = result.data.size == pageSize,
                        currentPage = page
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = result.error.message
                    )
                }
            }
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun refresh() {
        loadNews(refresh = true)
    }
}