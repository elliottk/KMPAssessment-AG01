package org.example.project.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AppColors(
    val primary: Color = Color(0xFF2196F3),
    val primaryVariant: Color = Color(0xFF1976D2),
    val secondary: Color = Color(0xFF4CAF50),
    val background: Color = Color(0xFFF5F5F5),
    val surface: Color = Color.White,
    val error: Color = Color(0xFFD32F2F),
    val warning: Color = Color(0xFFFF9800),
    val success: Color = Color(0xFF4CAF50),
    val onPrimary: Color = Color.White,
    val onSecondary: Color = Color.White,
    val onBackground: Color = Color(0xFF333333),
    val onSurface: Color = Color(0xFF333333),
    val onError: Color = Color.White,
    val onWarning: Color = Color.White,
    val onSuccess: Color = Color.White,
    val textPrimary: Color = Color(0xFF333333),
    val textSecondary: Color = Color(0xFF666666),
    val textHint: Color = Color(0xFF888888),
    val divider: Color = Color(0xFFE0E0E0),
    val disabled: Color = Color(0xFFE0E0E0),
    val placeholder: Color = Color(0xFFF5F5F5)
)

data class AppTypography(
    val h1: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF333333)
    ),
    val h2: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF333333)
    ),
    val h3: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF333333)
    ),
    val h4: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF333333)
    ),
    val body1: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF333333),
        lineHeight = 24.sp
    ),
    val body2: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF666666),
        lineHeight = 20.sp
    ),
    val caption: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF888888)
    ),
    val button: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White
    ),
    val overline: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4CAF50)
    )
)

data class AppDimensions(
    val paddingXSmall: Dp = 4.dp,
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,
    val paddingXLarge: Dp = 32.dp,
    val cornerRadius: Dp = 8.dp,
    val cornerRadiusSmall: Dp = 4.dp,
    val cornerRadiusLarge: Dp = 16.dp,
    val elevationSmall: Dp = 2.dp,
    val elevationMedium: Dp = 4.dp,
    val elevationLarge: Dp = 8.dp,
    val buttonHeight: Dp = 48.dp,
    val imageHeight: Dp = 200.dp,
    val dividerHeight: Dp = 1.dp
)

data class AppTheme(
    val colors: AppColors = AppColors(),
    val typography: AppTypography = AppTypography(),
    val dimensions: AppDimensions = AppDimensions()
)

val DarkColors = AppColors(
    primary = Color(0xFF90CAF9),
    primaryVariant = Color(0xFF42A5F5),
    secondary = Color(0xFF81C784),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = Color(0xFFCF6679),
    warning = Color(0xFFFFB74D),
    success = Color(0xFF81C784),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000),
    onWarning = Color(0xFF000000),
    onSuccess = Color(0xFF000000),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFBBBBBB),
    textHint = Color(0xFF777777),
    divider = Color(0xFF333333),
    disabled = Color(0xFF333333),
    placeholder = Color(0xFF2A2A2A)
)

val LightColors = AppColors()

val LocalAppColors = staticCompositionLocalOf { AppColors() }
val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    val typography = AppTypography().copy(
        h1 = AppTypography().h1.copy(color = colors.textPrimary),
        h2 = AppTypography().h2.copy(color = colors.textPrimary),
        h3 = AppTypography().h3.copy(color = colors.textPrimary),
        h4 = AppTypography().h4.copy(color = colors.textPrimary),
        body1 = AppTypography().body1.copy(color = colors.textPrimary),
        body2 = AppTypography().body2.copy(color = colors.textSecondary),
        caption = AppTypography().caption.copy(color = colors.textHint),
        button = AppTypography().button.copy(color = colors.onPrimary),
        overline = AppTypography().overline.copy(color = colors.secondary)
    )

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalAppDimensions provides AppDimensions()
    ) {
        content()
    }
}

object AppThemeValues {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val dimensions: AppDimensions
        @Composable
        get() = LocalAppDimensions.current
}