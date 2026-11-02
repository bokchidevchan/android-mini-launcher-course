package com.example.minilauncher.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

// 픽셀 런처처럼 회색 계열만 씁니다. 파랑은 진행률처럼 꼭 필요한 곳에만 나옵니다
private val LauncherColors = lightColorScheme(
    primary = Color(0xFF1A73E8),
    onPrimary = Color.White,
    secondaryContainer = Color(0xFFE8EAED),
    onSecondaryContainer = Color(0xFF202124),
    background = Color(0xFFF1F3F4),
    onBackground = Color(0xFF202124),
    surface = Color.White,
    onSurface = Color(0xFF202124),
    surfaceVariant = Color(0xFFE8EAED),
    onSurfaceVariant = Color(0xFF5F6368),
    outline = Color(0xFFDADCE0),
    error = Color(0xFFD93025),
)

// 화면 배경: 단색
val LauncherBackground: Brush = SolidColor(Color(0xFFF1F3F4))

@Composable
fun MiniLauncherTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LauncherColors, content = content)
}
