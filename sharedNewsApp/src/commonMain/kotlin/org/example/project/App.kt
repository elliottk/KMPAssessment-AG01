package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.di.AppModule
import org.example.project.ui.news.NewsListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.project.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    var isDarkTheme by remember { mutableStateOf(false) }
    AppTheme(darkTheme = isDarkTheme) {
        val newsViewModel = remember { AppModule.provideNewsViewModel() }
        NewsListScreen(
            viewModel = newsViewModel,
            isDarkTheme = isDarkTheme,
            onThemeToggle = { newTheme -> isDarkTheme = newTheme },
            onNewsClick = { news ->
                println("Clicked on news: ${news.title}")
            })
    }
}