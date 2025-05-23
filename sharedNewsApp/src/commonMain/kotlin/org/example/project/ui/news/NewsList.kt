package org.example.project.ui.news

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicText
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.example.project.ThemeSwitcher
import org.example.project.model.News
import org.example.project.resource.Strings
import org.example.project.ui.theme.AppThemeValues
import org.example.project.util.DateUtils

@Composable
fun NewsListScreen(
    viewModel: NewsViewModel,
    onNewsClick: (News) -> Unit = {},
    isDarkTheme: Boolean = false,
    onThemeToggle: (Boolean) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= uiState.news.size - 3 &&
                    !uiState.isLoading &&
                    !uiState.isLoadingMore &&
                    uiState.hasMore
                ) {
                    viewModel.loadNews()
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppThemeValues.colors.background)
    ) {
        // Header
        NewsHeader(
            onRefresh = { viewModel.refresh() },
            isRefreshing = uiState.isLoading && uiState.currentPage == 1,
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )

        // Error handling
        uiState.error?.let { error ->
            ErrorBanner(
                error = error,
                onDismiss = { viewModel.dismissError() },
                onRetry = { viewModel.loadNews() }
            )
        }

        // News list
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(AppThemeValues.dimensions.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(AppThemeValues.dimensions.paddingSmall)
        ) {
            items(uiState.news) { news ->
                NewsItem(
                    news = news,
                    onClick = { onNewsClick(news) }
                )
            }

            // Loading indicator at bottom
            if (uiState.isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }
            }
        }

        // Initial loading state
        if (uiState.isLoading && uiState.news.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        }

        // Empty state
        if (!uiState.isLoading && uiState.news.isEmpty() && uiState.error == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EmptyState()
            }
        }
    }
}

@Composable
private fun NewsHeader(
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppThemeValues.colors.surface)
            .padding(AppThemeValues.dimensions.paddingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = Strings.LATEST_NEWS,
            style = AppThemeValues.typography.h2
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppThemeValues.dimensions.paddingSmall)
        ) {
            ThemeSwitcher(
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )

            RefreshButton(
                onClick = onRefresh,
                isRefreshing = isRefreshing,
            )
        }
    }
}

@Composable
private fun RefreshButton(
    onClick: () -> Unit,
    isRefreshing: Boolean
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isRefreshing) Color(0xFFE0E0E0) else Color(0xFF2196F3))
            .clickable(enabled = !isRefreshing) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = if (isRefreshing) Strings.REFRESHING else Strings.REFRESH,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
@Composable
private fun NewsItem(
    news: News,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppThemeValues.dimensions.cornerRadius))
            .background(AppThemeValues.colors.surface)
            .clickable { onClick() }
            .padding(AppThemeValues.dimensions.paddingMedium)
    ) {
        news.media?.let { media ->
            KamelImage(
                resource = { asyncPainterResource(media.imageUrl) },
                contentDescription = news.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppThemeValues.dimensions.imageHeight)
                    .clip(RoundedCornerShape(AppThemeValues.dimensions.cornerRadius))
                    .background(AppThemeValues.colors.placeholder),
                onLoading = { progress ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BasicText(
                                text = Strings.CAMERA_ICON,
                                style = AppThemeValues.typography.h2.copy(
                                    color = AppThemeValues.colors.textSecondary
                                )
                            )
                            Spacer(modifier = Modifier.height(AppThemeValues.dimensions.paddingXSmall))
                            BasicText(
                                text = "${Strings.LOADING_WITH_PROGRESS}${(progress * 100).toInt()}%",
                                style = AppThemeValues.typography.caption.copy(
                                    color = AppThemeValues.colors.textHint
                                )
                            )
                        }
                    }
                },
                onFailure = { exception ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BasicText(
                                text = Strings.ERROR_ICON,
                                style = AppThemeValues.typography.h2.copy(
                                    color = AppThemeValues.colors.error
                                )
                            )
                            Spacer(modifier = Modifier.height(AppThemeValues.dimensions.paddingXSmall))
                            BasicText(
                                text = Strings.FAILED_TO_LOAD_IMAGE,
                                style = AppThemeValues.typography.caption.copy(
                                    color = AppThemeValues.colors.textHint
                                )
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(AppThemeValues.dimensions.paddingSmall))
        }

        // Title
        BasicText(
            text = news.title,
            style = AppThemeValues.typography.h4.copy(
                color = AppThemeValues.colors.textPrimary
            )
        )

        Spacer(modifier = Modifier.height(AppThemeValues.dimensions.paddingSmall))

        // Description
        BasicText(
            text = news.description,
            style = AppThemeValues.typography.body2.copy(
                color = AppThemeValues.colors.textSecondary
            )
        )

        Spacer(modifier = Modifier.height(AppThemeValues.dimensions.paddingSmall))

        // Footer with author and date
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = "${Strings.BY_AUTHOR}${news.author}",
                    style = AppThemeValues.typography.caption.copy(
                        color = AppThemeValues.colors.textHint
                    )
                )

                if (news.isLocal) {
                    Spacer(modifier = Modifier.width(AppThemeValues.dimensions.paddingSmall))
                    LocalBadge()
                }
            }

            BasicText(
                text = formatDate(news.publishedAtUnix),
                style = AppThemeValues.typography.caption.copy(
                    color = AppThemeValues.colors.textHint
                )
            )
        }
    }
}

@Composable
private fun LocalBadge() {
    BasicText(
        text = Strings.LOCAL_BADGE,
        style = AppThemeValues.typography.overline.copy(
            color = AppThemeValues.colors.secondary
        ),
        modifier = Modifier
            .border(
                width = AppThemeValues.dimensions.dividerHeight,
                color = AppThemeValues.colors.secondary,
                shape = RoundedCornerShape(AppThemeValues.dimensions.cornerRadiusSmall)
            )
            .padding(
                horizontal = AppThemeValues.dimensions.paddingXSmall + AppThemeValues.dimensions.paddingXSmall,
                vertical = AppThemeValues.dimensions.paddingXSmall / 2
            )
    )
}

@Composable
private fun ErrorBanner(
    error: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFEBEE))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            BasicText(
                text = Strings.ERROR,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
            )
            BasicText(
                text = error,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFFD32F2F)
                )
            )
        }

        Row {
            BasicText(
                text = Strings.RETRY,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .clickable { onRetry() }
                    .padding(8.dp)
            )

            BasicText(
                text = Strings.DISMISS,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF888888)
                ),
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    Color(0xFF2196F3),
                    RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = Strings.REFRESH_ICON,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        BasicText(
            text = Strings.LOADING,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
        )
    }
}

@Composable
private fun EmptyState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = Strings.NEWS_ICON,
            style = TextStyle(fontSize = 48.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicText(
            text = Strings.NO_NEWS_AVAILABLE,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF666666)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicText(
            text = Strings.TAB_REFRESH,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF888888)
            )
        )
    }
}

private fun formatDate(timestamp: Long): String {
    return DateUtils.formatToMMMddyyyyFromMillis(timestamp)
}