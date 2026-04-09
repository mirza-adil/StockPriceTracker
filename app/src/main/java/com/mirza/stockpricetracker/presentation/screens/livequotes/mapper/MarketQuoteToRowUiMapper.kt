package com.mirza.stockpricetracker.presentation.screens.livequotes.mapper

import com.mirza.stockpricetracker.domain.model.MarketQuote
import com.mirza.stockpricetracker.domain.model.QuoteTrend
import com.mirza.stockpricetracker.presentation.screens.livequotes.model.QuoteRowUiModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

class MarketQuoteToRowUiMapper {

    private val currency = NumberFormat.getCurrencyInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    fun toRowUi(quote: MarketQuote): QuoteRowUiModel {
        val delta = quote.price - quote.priorPrice
        val trend = when {
            delta > EPS -> QuoteTrend.Up
            delta < -EPS -> QuoteTrend.Down
            else -> QuoteTrend.Flat
        }
        val pct = if (abs(quote.priorPrice) > EPS) {
            (delta / quote.priorPrice) * 100.0
        } else {
            0.0
        }
        val sign = when {
            delta > EPS -> "+"
            delta < -EPS -> ""
            else -> ""
        }
        val changeText = sign + currency.format(abs(delta))
        val pctText = String.format(Locale.US, "%s%.2f%%", sign, abs(pct))

        return QuoteRowUiModel(
            symbol = quote.symbol,
            displayName = quote.displayName,
            priceText = currency.format(quote.price),
            priceValue = quote.price,
            changeText = changeText,
            changePercentText = pctText,
            trend = trend
        )
    }

    private companion object {
        const val EPS = 1e-6
    }
}
