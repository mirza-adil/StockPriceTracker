package com.mirza.stockpricetracker.domain.repository

import com.mirza.stockpricetracker.domain.model.MarketQuote
import com.mirza.stockpricetracker.domain.model.SessionLinkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MarketQuotesRepository {
    val sessionLink: StateFlow<SessionLinkState>
    fun quoteTicks(): Flow<MarketQuote>
    suspend fun beginStreaming()
    suspend fun endStreaming()
}
