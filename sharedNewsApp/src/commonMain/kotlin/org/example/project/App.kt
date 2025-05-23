package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.di.AppModule
import org.example.project.ui.news.NewsListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.example.project.ui.theme.AppThemeValues
import androidx.compose.foundation.text.BasicText
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

@Composable
fun ThemeSwitcher(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(AppThemeValues.dimensions.cornerRadius))
                    .background(AppThemeValues.colors.surface)
                    .clickable { onThemeToggle(!isDarkTheme) }
                    .padding(AppThemeValues.dimensions.paddingSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = if (isDarkTheme) "🌙" else "☀️",
                    style = AppThemeValues.typography.body1
                )
                Spacer(modifier = Modifier.width(AppThemeValues.dimensions.paddingXSmall))
                BasicText(
                    text = if (isDarkTheme) "Dark" else "Light",
                    style = AppThemeValues.typography.body2
                )
            }
}