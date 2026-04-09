package com.mirza.stockpricetracker.domain.usecase

import com.mirza.stockpricetracker.domain.model.MarketQuote
import com.mirza.stockpricetracker.domain.repository.MarketQuotesRepository
import kotlinx.coroutines.flow.Flow

class ObserveQuoteStreamUseCase(
    private val repository: MarketQuotesRepository
) {
    operator fun invoke(): Flow<MarketQuote> = repository.quoteTicks()
}
