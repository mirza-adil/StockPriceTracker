package com.mirza.stockpricetracker.presentation.screens.livequotes.model

import androidx.compose.runtime.Immutable
import com.mirza.stockpricetracker.domain.model.QuoteTrend

@Immutable
data class QuoteRowUiModel(
    val symbol: String,
    val displayName: String,
    val priceText: String,
    val priceValue: Double,
    val changeText: String,
    val changePercentText: String,
    val trend: QuoteTrend
)
