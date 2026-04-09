package com.mirza.stockpricetracker.domain.usecase

import com.mirza.stockpricetracker.domain.model.SessionLinkState
import com.mirza.stockpricetracker.domain.repository.MarketQuotesRepository
import kotlinx.coroutines.flow.StateFlow

class ManageSessionLinkUseCase(
    private val repository: MarketQuotesRepository
) {
    fun sessionLink(): StateFlow<SessionLinkState> = repository.sessionLink

    suspend fun beginStreaming() {
        repository.beginStreaming()
    }

    suspend fun endStreaming() {
        repository.endStreaming()
    }
}
