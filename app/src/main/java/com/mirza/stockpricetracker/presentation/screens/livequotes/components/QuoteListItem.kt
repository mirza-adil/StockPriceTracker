package com.mirza.stockpricetracker.presentation.screens.livequotes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.mirza.stockpricetracker.designsystem.components.DeltaBadge
import com.mirza.stockpricetracker.designsystem.theme.BoardSpacing
import com.mirza.stockpricetracker.designsystem.theme.BoardTypography
import com.mirza.stockpricetracker.designsystem.theme.boardColorScheme
import com.mirza.stockpricetracker.presentation.screens.livequotes.model.QuoteRowUiModel

@Composable
fun QuoteListItem(
    row: QuoteRowUiModel,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true
) {
    val colors = boardColorScheme()
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = BoardSpacing.medium,
                    vertical = BoardSpacing.mediumSmall
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(BoardSpacing.extraSmall)
            ) {
                Text(
                    text = row.symbol,
                    style = BoardTypography.rowSymbol,
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = row.displayName,
                    style = BoardTypography.rowSecondary,
                    color = colors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(BoardSpacing.extraSmall)
            ) {
                Text(
                    text = row.priceText,
                    style = BoardTypography.rowPrice,
                    color = colors.textPrimary
                )
                DeltaBadge(
                    changeText = row.changeText,
                    percentText = row.changePercentText,
                    trend = row.trend
                )
            }
        }
        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = BoardSpacing.medium),
                color = colors.divider
            )
        }
    }
}
