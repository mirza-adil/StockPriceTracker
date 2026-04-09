package com.mirza.stockpricetracker.domain.usecase

import com.mirza.stockpricetracker.domain.model.MarketQuote

class SortQuotesByPriceUseCase {
    operator fun invoke(quotes: List<MarketQuote>): List<MarketQuote> =
        quotes.sortedByDescending { it.price }
}
