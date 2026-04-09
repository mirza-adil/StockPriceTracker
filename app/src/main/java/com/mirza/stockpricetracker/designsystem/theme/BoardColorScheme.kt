package com.mirza.stockpricetracker.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class BoardColorScheme(
    val primaryBackground: Color,
    val screenBackground: Color,
    val positive: Color,
    val negative: Color,
    val neutral: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val sessionConnected: Color,
    val sessionDisconnected: Color,
    val sessionConnecting: Color,
    val sessionError: Color,
    val divider: Color,
    val headerActionFill: Color,
    val headerActionContent: Color
)

internal val DarkBoardColors = BoardColorScheme(
    primaryBackground = Color(0xFF0F1217),
    screenBackground = Color(0xFF0F1217),
    positive = Color(0xFF4CAF50),
    negative = Color(0xFFEF5350),
    neutral = Color(0xFF9E9E9E),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFB0B0B0),
    textTertiary = Color(0xFF768089),
    sessionConnected = Color(0xFF4CAF50),
    sessionDisconnected = Color(0xFFF44336),
    sessionConnecting = Color(0xFFFF9800),
    sessionError = Color(0xFFD32F2F),
    divider = Color(0xFF3A3A3A),
    headerActionFill = Color(0xFF42A5F5),
    headerActionContent = Color(0xFF1A237E)
)

internal val LightBoardColors = BoardColorScheme(
    primaryBackground = EggWhite,
    screenBackground = EggWhite,
    positive = Color(0xFF2E7D32),
    negative = Color(0xFFC62828),
    neutral = Color(0xFF757575),
    textPrimary = Color(0xFF1C1C1E),
    textSecondary = Color(0xFF6D6D70),
    textTertiary = Color(0xFF8E8E93),
    sessionConnected = Color(0xFF2E7D32),
    sessionDisconnected = Color(0xFFC62828),
    sessionConnecting = Color(0xFFE65100),
    sessionError = Color(0xFFB71C1C),
    divider = Color(0xFFE6E2DB),
    headerActionFill = Color(0xFF1E88E5),
    headerActionContent = Color(0xFFFFFFFF)
)

val LocalBoardColors = compositionLocalOf { DarkBoardColors }

@Composable
fun boardColorScheme(): BoardColorScheme = LocalBoardColors.current
