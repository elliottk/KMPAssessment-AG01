package org.example.project.ui.news

import org.example.project.model.News

data class NewsUiState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentPage: Int = 0
)