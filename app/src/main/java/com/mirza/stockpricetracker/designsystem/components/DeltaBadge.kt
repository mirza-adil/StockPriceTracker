package com.mirza.stockpricetracker.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mirza.stockpricetracker.designsystem.theme.BoardSpacing
import com.mirza.stockpricetracker.designsystem.theme.BoardTypography
import com.mirza.stockpricetracker.designsystem.theme.boardColorScheme
import com.mirza.stockpricetracker.domain.model.QuoteTrend

@Composable
fun DeltaBadge(
    changeText: String,
    percentText: String,
    trend: QuoteTrend,
    modifier: Modifier = Modifier
) {
    val colors = boardColorScheme()
    val (bg, fg) = when (trend) {
        QuoteTrend.Up -> colors.positive.copy(alpha = 0.18f) to colors.positive
        QuoteTrend.Down -> colors.negative.copy(alpha = 0.18f) to colors.negative
        QuoteTrend.Flat -> colors.neutral.copy(alpha = 0.14f) to colors.neutral
    }
    val arrow = when (trend) {
        QuoteTrend.Up -> "▲"
        QuoteTrend.Down -> "▼"
        QuoteTrend.Flat -> "◆"
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = BoardSpacing.small, vertical = BoardSpacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BoardSpacing.extraSmall)
    ) {
        Text(
            text = arrow,
            style = BoardTypography.badge,
            color = fg,
            maxLines = 1
        )
        Text(
            text = changeText,
            style = BoardTypography.badge,
            color = fg,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
        Text(
            text = "($percentText)",
            style = BoardTypography.badge,
            color = fg.copy(alpha = 0.92f),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}
